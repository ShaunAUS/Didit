package snoopy.didit.domain.alarm

import org.springframework.stereotype.Component
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import java.time.LocalTime

@Component
class AlarmAppender(
    private val alarmRepository: AlarmRepository,
) {
    fun createAlarm(
        alarmCreateDto: AlarmCreateDto,
        twentyFourHourTime: LocalTime,
        memberId: Long,
    ): Alarm {
        return alarmRepository.createAlarm(alarmCreateDto, twentyFourHourTime, memberId)
    }

    fun save(alarm: Alarm) {
        alarmRepository.save(alarm)
    }
}
