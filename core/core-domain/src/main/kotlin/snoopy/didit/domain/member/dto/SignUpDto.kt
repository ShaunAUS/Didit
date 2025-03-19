package snoopy.didit.domain.member.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.member.JobFamily
import snoopy.didit.member.JobRole
import snoopy.didit.skill.Skill

@Schema(description = "회원가입 요청 DTO")
data class SignUpDto(
    @Schema(description = "이메일", example = "didit@gmail.com", required = true)
    @field:NotBlank(message = "이메일은 필수 입력값입니다")
    val email: String?,
    @Schema(description = "비밀번호", example = "test12341234", required = true)
    @field:NotBlank(message = "비밀번호는 필수 입력값입니다")
    val password: String?,
    @Schema(description = "이름", example = "석규나", required = true)
    @field:NotBlank(message = "이름은 필수 입력값입니다")
    val name: String?,
    @Schema(description = "닉네임", example = "사기꾼", required = true)
    @field:NotBlank(message = "닉네임은 필수 입력값입니다")
    val nickName: String?,
    @Schema(description = "직군", example = "DEVELOPMENT", required = false)
    val jobFamily: JobFamily?,
    @Schema(description = "직책", example = "BACKEND_DEVELOPER", required = false)
    val jobRole: JobRole?,
    @Schema(description = "기술 목록", example = "[\"ASANA\", \"AWS\", \"BITBUCKET\"]", required = false)
    val memberSkills: List<Skill>?,
    @Schema(
        description = "알람 정보",
        example =
            "{\"weekDay\":[\"MONDAY\",\"WEDNESDAY\",\"FRIDAY\"],\"time\":\"12:20:00\",\"timeOfDay\":\"PM\"" +
                ",\"isAlarmEnable\":true}",
        required = true,
    )
    val alarm: AlarmCreateDto?,
)
