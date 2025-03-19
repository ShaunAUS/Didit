package snoopy.didit.domain.alarmweekday

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import snoopy.didit.memoir.Weekday

@Entity
class AlarmWeekDay(
    alarmId: Long,
    weekday: Int,
) {
    @Column(nullable = false, length = 1)
    var weekday: Int = weekday
        protected set

    @Column(nullable = false)
    var alarmId: Long = alarmId
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun of(
            weekDay: Weekday,
            alarmId: Long,
        ): AlarmWeekDay {
            return AlarmWeekDay(
                alarmId = alarmId,
                weekday = Weekday.toInt(weekDay),
            )
        }
    }
}
