package snoopy.didit.domain.alarm

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import java.time.LocalTime

@Entity
class Alarm(
    time: LocalTime,
    memberId: Long,
    isAlarmEnable: Boolean,
) : BaseEntity() {
    @Column(nullable = false)
    var time: LocalTime = time
        protected set

    @Column(nullable = false)
    var isAlarmEnable: Boolean = isAlarmEnable
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    fun updateAlarmEnable(isAlarmEnable: Boolean) {
        this.isAlarmEnable = isAlarmEnable
    }

    fun updateAlarmTime(twentyFourHour: LocalTime) {
        time = twentyFourHour
    }

    companion object {
        fun of(
            alarmCreateDto: AlarmCreateDto,
            twentyFourHourTime: LocalTime,
            memberId: Long,
        ): Alarm {
            return Alarm(
                time = twentyFourHourTime,
                memberId = memberId,
                isAlarmEnable = alarmCreateDto.isAlarmEnable!!,
            )
        }
    }

    fun isSameHour(nowHour: Int): Boolean {
        return time.hour == nowHour
    }

    fun isSameMinute(nowMinute: Int): Boolean {
        return time.minute == nowMinute
    }
}
