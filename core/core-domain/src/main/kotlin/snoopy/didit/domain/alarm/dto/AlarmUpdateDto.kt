package snoopy.didit.domain.alarm.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import snoopy.didit.memoir.TimeOfDay
import snoopy.didit.memoir.Weekday
import java.time.LocalTime

@Schema(description = "알람 수정 요청 DTO")
data class AlarmUpdateDto(
    @Schema(description = "알림 요일 목록", example = "[\"MONDAY\", \"WEDNESDAY\", \"FRIDAY\"]", required = true)
    @field:NotEmpty(message = "알림요일 선택은 필수 입력값입니다")
    val weekDay: List<Weekday>?,
    @Schema(
        description = "알람 시간",
        example = "12:20:00",
        format = "HH:mm:ss",
        type = "string",
        required = true,
    )
    @field:NotNull(message = "알람 시간은 필수 입력값입니다")
    val time: LocalTime?,
    @Schema(description = "오전/오후", example = "PM", required = true)
    @field:NotNull(message = "오전/오후 선택은 필수 입력값입니다")
    val timeOfDay: TimeOfDay?,
)
