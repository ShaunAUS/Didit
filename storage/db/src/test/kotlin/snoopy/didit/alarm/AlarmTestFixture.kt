package snoopy.didit.alarm

import snoopy.didit.domain.alarm.Alarm
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.memoir.TimeOfDay
import snoopy.didit.memoir.Weekday
import java.time.LocalTime

internal class AlarmTestFixture {
    companion object {
        fun getAlarmCreateDto(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = listOf(Weekday.MONDAY, Weekday.WEDNESDAY),
                time = LocalTime.of(12, 0),
                timeOfDay = TimeOfDay.AM,
                isAlarmEnable = true,
            )
        }

        fun getAlarm(): Alarm {
            return Alarm(
                time = LocalTime.of(12, 0),
                memberId = 1,
                isAlarmEnable = true,
            )
        }
    }
}
