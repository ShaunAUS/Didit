package snoopy.didit.domain.alarm

import getOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.domain.alarm.dto.AlarmResponse
import snoopy.didit.domain.alarm.dto.AlarmUpdateDto
import snoopy.didit.domain.alarm.dto.toResponse
import snoopy.didit.domain.alarmweekday.AlarmWeekDay
import snoopy.didit.domain.alarmweekday.AlarmWeekDayService
import java.time.LocalTime

private const val PM = "PM"
private const val TWELEVEHOURS = 12L

@Service
class AlarmFacade(
    private val alarmService: AlarmService,
    private val alarmWeekDayService: AlarmWeekDayService,
) {
    @Transactional
    fun createAlarm(
        alarmCreateDto: AlarmCreateDto,
        memberId: Long,
    ): AlarmResponse {
        val twentyFourHourTime = convertTimeToTwentyFourHourTimeByTimeOfDay(alarmCreateDto)
        val createdAlarm = alarmService.createAlarm(alarmCreateDto, twentyFourHourTime, memberId)
        return createdAlarm.toResponse(createdAlarm, createAlamWeekDays(alarmCreateDto, createdAlarm))
    }

    @Transactional(readOnly = true)
    fun findMyAlarmInfo(memberId: Long): AlarmResponse {
        val alarm = findAlarmBy(memberId)
        val alarmWeekDays = alarmWeekDayService.findByAlarmId(alarm.id.getOrThrow())
        return alarm.toResponse(alarm, alarmWeekDays)
    }

    @Transactional
    fun updateEmailAlarmEnable(
        isAlarmEnable: Boolean,
        memberId: Long,
    ) {
        val alarm = findAlarmBy(memberId)
        alarm.updateAlarmEnable(isAlarmEnable)
        alarmService.save(alarm)
    }

    @Transactional
    fun updateAlarm(
        alarmUpdateDto: AlarmUpdateDto,
        memberId: Long,
    ): AlarmResponse {
        val alarm = findAlarmBy(memberId)
        alarm.updateAlarmTime(convertTimeToTwentyFourHourTimeByTimeOfDay(alarmUpdateDto))
        deleteTags(alarm)
        return alarm.toResponse(alarm, createAlarmWeekDays(alarmUpdateDto, alarm))
    }

    private fun deleteTags(alarm: Alarm) {
        alarmWeekDayService.deleteByAlarmId(alarm.id.getOrThrow())
    }

    private fun findAlarmBy(memberId: Long) = alarmService.findByMemberId(memberId)

    private fun convertTimeToTwentyFourHourTimeByTimeOfDay(dto: Any): LocalTime {
        val (timeOfDay, time) =
            when (dto) {
                is AlarmCreateDto -> {
                    val timeOfDay = dto.timeOfDay.getOrThrow()
                    val time = dto.time.getOrThrow()
                    timeOfDay.name to time
                }

                is AlarmUpdateDto -> {
                    val timeOfDay = dto.timeOfDay.getOrThrow()
                    val time = dto.time.getOrThrow()
                    timeOfDay.name to time
                }

                else -> throw IllegalArgumentException("Invalid DTO type")
            }

        var twentyFourHourTime = time
        if (PM.equals(timeOfDay)) {
            twentyFourHourTime = time.plusHours(TWELEVEHOURS)
        }
        return twentyFourHourTime
    }

    private fun createAlamWeekDays(
        alarmCreateDto: AlarmCreateDto,
        createdAlarm: Alarm,
    ): List<AlarmWeekDay> {
        val createAlarmWeekDay =
            alarmWeekDayService.createAlarmWeekDay(
                alarmCreateDto.weekDay.getOrThrow(),
                createdAlarm.id.getOrThrow(),
            )
        return createAlarmWeekDay
    }

    private fun createAlarmWeekDays(
        alarmUpdateDto: AlarmUpdateDto,
        alarm: Alarm,
    ): List<AlarmWeekDay> {
        val createAlarmWeekDay =
            alarmWeekDayService.createAlarmWeekDay(
                alarmUpdateDto.weekDay.getOrThrow(),
                alarm.id.getOrThrow(),
            )
        return createAlarmWeekDay
    }
}
