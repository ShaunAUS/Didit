package snoopy.didit.domain.alarmweekday

import snoopy.didit.memoir.Weekday

interface AlarmWeekDayRepository {
    fun createAlarmWeekDay(
        weekDay: List<Weekday>,
        alarmId: Long,
    ): List<AlarmWeekDay>

    fun findByAlarmId(alarmId: Long): List<AlarmWeekDay>

    fun deleteByAlarmId(alarmId: Long)
}
