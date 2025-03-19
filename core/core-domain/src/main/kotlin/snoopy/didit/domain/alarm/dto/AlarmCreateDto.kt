package snoopy.didit.domain.alarm.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import snoopy.didit.memoir.TimeOfDay
import snoopy.didit.memoir.Weekday
import java.time.LocalTime

@Schema(description = "알람 생성 요청 DTO")
data class AlarmCreateDto(
    @field:NotEmpty(message = "알림요일 선택은 필수 입력값입니다")
    @Schema(description = "알림 요일 목록", example = "[\"MONDAY\", \"WEDNESDAY\", \"FRIDAY\"]", required = true)
    val weekDay: List<Weekday>?,
    @field:NotNull(message = "알람 시간은 필수 입력값입니다")
    @Schema(
        description = "알람 시간",
        example = "12:20:00",
        format = "HH:mm:ss",
        type = "string",
        required = true,
    )
    val time: LocalTime?,
    @field:NotNull(message = "오전/오후 선택은 필수 입력값입니다")
    @Schema(description = "오전/오후", example = "PM", required = true)
    val timeOfDay: TimeOfDay?,
    @field:NotNull(message = "이메일 알람 활성화 여부는 필수 입력값입니다")
    @Schema(description = "알람 활성화 여부", example = "true", required = true)
    val isAlarmEnable: Boolean?,
)
