package snoopy.didit.domain.alarm

import org.springframework.stereotype.Component

@Component
class AlarmFinder(
    private val alarmRepository: AlarmRepository,
) {
    fun findMyAlarmBy(memberId: Long): Alarm {
        return alarmRepository.findMyAlarmBy(memberId)
    }
}
