package snoopy.didit.fixture

import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.domain.alarm.dto.AlarmResponse
import snoopy.didit.domain.alarm.dto.AlarmUpdateDto
import snoopy.didit.memoir.TimeOfDay
import snoopy.didit.memoir.Weekday
import java.time.LocalTime

internal class AlarmControllerTestFixture {
    companion object {
        fun getCreateAlarmRequest(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                time = LocalTime.of(12, 12),
                timeOfDay = TimeOfDay.PM,
                isAlarmEnable = true,
            )
        }

        fun getAlarmInfoResponse(): AlarmResponse {
            return AlarmResponse(
                time = LocalTime.of(12, 12),
                day = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                isAlarmEnable = true,
            )
        }

        fun getUpdateAlarmRequest(): AlarmUpdateDto {
            return AlarmUpdateDto(
                weekDay = listOf(Weekday.WEDNESDAY, Weekday.THURSDAY),
                time = LocalTime.of(9, 30),
                timeOfDay = TimeOfDay.AM,
            )
        }

        fun getUpdatedAlarmInfoResponse(): AlarmResponse {
            return AlarmResponse(
                time = LocalTime.of(9, 30),
                day = listOf(Weekday.WEDNESDAY, Weekday.THURSDAY),
                isAlarmEnable = true,
            )
        }

        fun getNullTimeCreateAlarmRequest(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                time = null,
                timeOfDay = TimeOfDay.PM,
                isAlarmEnable = true,
            )
        }

        fun getNullTimeOfDayCreateAlarmRequest(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                time = LocalTime.of(12, 12),
                timeOfDay = null,
                isAlarmEnable = true,
            )
        }

        fun getNullWeekDayCreateAlarmRequest(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = null,
                time = LocalTime.of(12, 12),
                timeOfDay = TimeOfDay.PM,
                isAlarmEnable = true,
            )
        }

        fun getNullIsAlarmEnableCreateAlarmRequest(): AlarmCreateDto {
            return AlarmCreateDto(
                weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                time = LocalTime.of(12, 12),
                timeOfDay = TimeOfDay.PM,
                isAlarmEnable = null,
            )
        }
    }
}
