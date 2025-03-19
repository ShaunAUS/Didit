package snoopy.didit.domain.alarm

import org.springframework.stereotype.Repository
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import java.time.LocalTime

@Repository
interface AlarmRepository {
    fun createAlarm(
        alarmCreateDto: AlarmCreateDto,
        twentyFourHourTime: LocalTime,
        memberId: Long,
    ): Alarm

    fun findMyAlarmBy(memberId: Long): Alarm

    fun save(alarm: Alarm)
}
