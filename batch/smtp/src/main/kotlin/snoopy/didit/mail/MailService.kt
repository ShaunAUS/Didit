package snoopy.didit.mail

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.alarm.Alarm
import snoopy.didit.domain.alarm.AlarmService
import snoopy.didit.domain.alarmweekday.AlarmWeekDayService
import snoopy.didit.domain.member.Member
import snoopy.didit.domain.member.MemberService
import snoopy.didit.domain.memoir.MemoirService
import snoopy.didit.domain.randommessage.RandomMessageService
import snoopy.didit.memoir.Weekday
import java.time.DayOfWeek
import java.time.LocalDateTime

private const val MONDAY = "월요일"
private const val TUESDAY = "화요일"
private const val WEDNESDAY = "수요일"
private const val THURSDAY = "목요일"
private const val FRIDAY = "금요일"

@Service
@Transactional(readOnly = true)
class MailService(
    private val javaMailSender: JavaMailSender,
    private val memberService: MemberService,
    private val alarmService: AlarmService,
    private val memoirService: MemoirService,
    private val randomMessageService: RandomMessageService,
    private val alarmWeekDayService: AlarmWeekDayService,
) {
    // @Scheduled(cron = "0 */10 * * * MON-FRI") , 10분마다실행 (Production)
    // @Scheduled(cron = "0 * * * * MON-FRI"), 1분마다 실행 (Test)
    suspend fun sendEmailNotice() {
        val membersWhoDidNotWriteTodayMemoir = findMembersWhoDidNotWriteTodayMemoir()
        val targetMembers = getEmailTargetListByAlarm(membersWhoDidNotWriteTodayMemoir)

        withContext(Dispatchers.IO) {
            for (targetMember in targetMembers) {
                val mimeMessage = javaMailSender.createMimeMessage()
                try {
                    MimeMessageHelper(mimeMessage, false, "UTF-8").apply {
                        setTo(targetMember.email)
                        setSubject("Didit" + "  " + targetMember.name + "님, 오늘 회고록을 작성해주세요.")
                        setText(
                            "안녕하세요. " + targetMember.name + "님!\n" +
                                "\n" + // 추가 공백
                                randomMessageService.createEmailRandomMessage() + "\n" +
                                "\n" + // 추가 공백
                                "링크 : www.naver.com" + "\n" +
                                "\n" + // 추가 공백
                                "좋은 하루 보내세요!\n" +
                                "\n" + // 추가 공백
                                "Didit 드림\n",
                        )
                    }
                    javaMailSender.send(mimeMessage)
                } catch (e: Exception) {
                    // TODO 메일 실패 사용자 로그
                    throw RuntimeException("메일 알림 실패", e)
                }
            }
        }
    }

    private suspend fun getEmailTargetListByAlarm(didNotWriteTodayMemoirMembers: List<Member>): List<Member> =
        coroutineScope {
            val now = LocalDateTime.now()
            val nowHour = now.hour
            val nowMinute = now.minute
            val nowDayOfWeek = getDayOfWeek(now)

            val deferredResults =
                didNotWriteTodayMemoirMembers.map { member ->
                    async(Dispatchers.IO) {
                        val alarm = alarmService.findByMemberId(member.id!!)
                        val alarmWeekDay = alarmWeekDayService.findByAlarmId(alarm.id!!)
                        alarmWeekDay
                            .filter { Weekday.toWeekday(it.weekday).isSameDayOfweek(nowDayOfWeek) }
                            .filter { isSameHourAndMinute(alarm, nowHour, nowMinute) }
                            .filter { alarm.isAlarmEnable }
                            .map { member }
                    }
                }

            deferredResults.awaitAll().flatten()
        }

    private fun isSameHourAndMinute(
        alarm: Alarm,
        nowHour: Int,
        nowMinute: Int,
    ) = alarm.isSameHour(nowHour) && alarm.isSameMinute(nowMinute)

    private fun findMembersWhoDidNotWriteTodayMemoir(): List<Member> {
        return memberService.findAllMembers()
            .filter { member -> memoirService.findTodayMemoirsBy(member.id!!).isNullOrEmpty() }
    }

    private fun getDayOfWeek(now: LocalDateTime): String {
        return when (now.dayOfWeek) {
            DayOfWeek.MONDAY -> MONDAY
            DayOfWeek.TUESDAY -> TUESDAY
            DayOfWeek.WEDNESDAY -> WEDNESDAY
            DayOfWeek.THURSDAY -> THURSDAY
            DayOfWeek.FRIDAY -> FRIDAY
            else -> throw IllegalArgumentException("Invalid day of week")
        }
    }
}
