package snoopy.didit.domain.alarm.dto

import snoopy.didit.domain.alarm.Alarm
import snoopy.didit.domain.alarmweekday.AlarmWeekDay
import snoopy.didit.memoir.Weekday
import java.time.LocalTime

data class AlarmResponse(
    val time: LocalTime,
    val day: List<Weekday>,
    val isAlarmEnable: Boolean,
)

fun Alarm.toResponse(
    createdAlarms: Alarm,
    createAlarmWeekDay: List<AlarmWeekDay>,
): AlarmResponse {
    return AlarmResponse(
        time = createdAlarms.time,
        day = createAlarmWeekDay.map { alarmWeekDay -> Weekday.toWeekday(alarmWeekDay.weekday) },
        isAlarmEnable = createdAlarms.isAlarmEnable,
    )
}
