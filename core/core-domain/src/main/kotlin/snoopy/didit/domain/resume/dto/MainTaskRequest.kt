package snoopy.didit.domain.resume.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import snoopy.didit.domain.resume.MainTask
import java.time.LocalDateTime

data class MainTaskRequest(
    @field:PastOrPresent(message = "근무 시작일은 현재 또는 과거 날짜여야 합니다")
    val workStartDate: LocalDateTime? = null,
    @field:PastOrPresent(message = "근무 종료일은 현재 또는 과거 날짜여야 합니다")
    val workFinishDate: LocalDateTime? = null,
    @field:NotBlank(message = "성과를 입력해주세요")
    val achievement: String,
    @field:NotBlank(message = "설명을 입력해주세요")
    val explain: String,
    @field:NotNull(message = "경력 ID는 필수입니다")
    @field:Positive(message = "올바른 경력 ID를 입력해주세요")
    val careerId: Long,
)

fun MainTaskRequest.toDomain() =
    MainTask(
        workStartDate = this.workStartDate,
        workFinishDate = this.workFinishDate,
        achievement = this.achievement,
        explanation = this.explain,
        careerId = this.careerId,
    )
