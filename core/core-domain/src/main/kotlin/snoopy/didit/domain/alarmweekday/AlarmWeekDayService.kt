package snoopy.didit.domain.alarmweekday

import org.springframework.stereotype.Service
import snoopy.didit.memoir.Weekday

@Service
class AlarmWeekDayService(
    private val alarmWeekdayFinder: AlarmWeekDayFinder,
    private val alarmWeekDayAppender: AlarmWeekDayAppender,
) {
    fun createAlarmWeekDay(
        weekDay: List<Weekday>,
        alarmId: Long,
    ): List<AlarmWeekDay> {
        return alarmWeekDayAppender.createAlarmWeekDay(weekDay, alarmId)
    }

    fun findByAlarmId(alarmId: Long): List<AlarmWeekDay> {
        return alarmWeekdayFinder.findByAlarmId(alarmId)
    }

    fun deleteByAlarmId(alarmId: Long) {
        alarmWeekdayFinder.deleteByAlarmId(alarmId)
    }
}
