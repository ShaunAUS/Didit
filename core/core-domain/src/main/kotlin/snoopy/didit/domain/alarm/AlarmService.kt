package snoopy.didit.domain.alarm

import org.springframework.stereotype.Service
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import java.time.LocalTime

@Service
class AlarmService(
    private val alarmFinder: AlarmFinder,
    private val alarmAppender: AlarmAppender,
) {
    fun createAlarm(
        alarmCreateDto: AlarmCreateDto,
        twentyFourHourTime: LocalTime,
        memberId: Long,
    ): Alarm {
        return alarmAppender.createAlarm(alarmCreateDto, twentyFourHourTime, memberId)
    }

    fun findByMemberId(memberId: Long): Alarm {
        return alarmFinder.findMyAlarmBy(memberId)
    }

    fun save(alarm: Alarm) {
        alarmAppender.save(alarm)
    }
}
