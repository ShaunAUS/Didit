package snoopy.didit.domain.alarmweekday

import org.springframework.stereotype.Component

@Component
class AlarmWeekDayFinder(
    private val alarmWeekDayRepository: AlarmWeekDayRepository,
) {
    fun findByAlarmId(alarmId: Long): List<AlarmWeekDay> {
        return alarmWeekDayRepository.findByAlarmId(alarmId)
    }

    fun deleteByAlarmId(alarmId: Long) {
        alarmWeekDayRepository.deleteByAlarmId(alarmId)
    }
}
