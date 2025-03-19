package snoopy.didit.domain.alarmweekday

import org.springframework.stereotype.Component
import snoopy.didit.memoir.Weekday

@Component
class AlarmWeekDayAppender(
    private val alarmWeekDayRepository: AlarmWeekDayRepository,
) {
    fun createAlarmWeekDay(
        weekDay: List<Weekday>,
        alarmId: Long,
    ): List<AlarmWeekDay> {
        return alarmWeekDayRepository.createAlarmWeekDay(weekDay, alarmId)
    }
}
