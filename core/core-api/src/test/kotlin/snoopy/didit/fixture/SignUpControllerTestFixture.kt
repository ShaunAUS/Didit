package snoopy.didit.fixture

import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.domain.member.dto.SignUpDto
import snoopy.didit.member.JobFamily
import snoopy.didit.member.JobRole
import snoopy.didit.memoir.TimeOfDay
import snoopy.didit.memoir.Weekday
import snoopy.didit.skill.Skill
import java.time.LocalTime

class SignUpControllerTestFixture {
    companion object {
        fun getSignUpDto(): SignUpDto {
            return SignUpDto(
                email = "didit@gmail.com",
                password = "test12341234",
                name = "석규나",
                nickName = "사기꾼",
                jobFamily = JobFamily.DEVELOPMENT,
                jobRole = JobRole.BACKEND_DEVELOPER,
                memberSkills = listOf(Skill.ASANA, Skill.AWS, Skill.BITBUCKET),
                alarm =
                    AlarmCreateDto(
                        weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                        time = LocalTime.of(12, 12),
                        timeOfDay = TimeOfDay.PM,
                        isAlarmEnable = true,
                    ),
            )
        }

        fun getNullEmailSignUpDto(): SignUpDto {
            return SignUpDto(
                email = "",
                password = "test12341234",
                name = "석규나",
                nickName = "사기꾼",
                jobFamily = JobFamily.DEVELOPMENT,
                jobRole = JobRole.BACKEND_DEVELOPER,
                memberSkills = listOf(Skill.ASANA, Skill.AWS, Skill.BITBUCKET),
                alarm =
                    AlarmCreateDto(
                        weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                        time = LocalTime.of(12, 12),
                        timeOfDay = TimeOfDay.PM,
                        isAlarmEnable = true,
                    ),
            )
        }

        fun getNullPassWordSignUpDto(): SignUpDto {
            return SignUpDto(
                email = "didit@gmail.com",
                password = "",
                name = "석규나",
                nickName = "사기꾼",
                jobFamily = JobFamily.DEVELOPMENT,
                jobRole = JobRole.BACKEND_DEVELOPER,
                memberSkills = listOf(Skill.ASANA, Skill.AWS, Skill.BITBUCKET),
                alarm =
                    AlarmCreateDto(
                        weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                        time = LocalTime.of(12, 12),
                        timeOfDay = TimeOfDay.PM,
                        isAlarmEnable = true,
                    ),
            )
        }

        fun getNullNameSignUpDto(): SignUpDto {
            return SignUpDto(
                email = "didit@gmail.com",
                password = "test12341234",
                name = "",
                nickName = "사기꾼",
                jobFamily = JobFamily.DEVELOPMENT,
                jobRole = JobRole.BACKEND_DEVELOPER,
                memberSkills = listOf(Skill.ASANA, Skill.AWS, Skill.BITBUCKET),
                alarm =
                    AlarmCreateDto(
                        weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                        time = LocalTime.of(12, 12),
                        timeOfDay = TimeOfDay.PM,
                        isAlarmEnable = true,
                    ),
            )
        }

        fun getNullNickNameSignUpDto(): SignUpDto {
            return SignUpDto(
                email = "didit@gmail.com",
                password = "test12341234",
                name = "석규나",
                nickName = "",
                jobFamily = JobFamily.DEVELOPMENT,
                jobRole = JobRole.BACKEND_DEVELOPER,
                memberSkills = listOf(Skill.ASANA, Skill.AWS, Skill.BITBUCKET),
                alarm =
                    AlarmCreateDto(
                        weekDay = listOf(Weekday.MONDAY, Weekday.TUESDAY),
                        time = LocalTime.of(12, 12),
                        timeOfDay = TimeOfDay.PM,
                        isAlarmEnable = true,
                    ),
            )
        }
    }
}
