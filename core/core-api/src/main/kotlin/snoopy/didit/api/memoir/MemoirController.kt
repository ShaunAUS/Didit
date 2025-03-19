package snoopy.didit.api.memoir

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.MemoirResponse
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto
import snoopy.didit.domain.memoritag.MemoirTagFacade
import snoopy.didit.domain.memoritag.dto.MemoirTagResponse
import snoopy.didit.swagger.annotation.ApiDoc
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1/memoirs")
@Tag(name = "Memoir Controller", description = "Memoir API")
class MemoirController(
    private val memoirTagFacade: MemoirTagFacade,
) {
    @ApiDoc(
        summary = "회고 생성",
        description = "새로운 회고를 생성한다",
        success = "memoirTag.success.response",
        error = "memoirTag.valid.error.response",
    )
    @PostMapping("")
    fun createMemoir(
        @RequestBody @Valid createMemoir: MemoirCreateDto,
    ): Response<MemoirTagResponse> {
        return Response.success(memoirTagFacade.createMemoirTag(createMemoir))
    }

    @ApiDoc(
        summary = "회고 조회",
        description = "특정 회고를 조회한다",
        success = "memoirTag.success.response",
        error = "memoirTag.not.found.error.response",
    )
    @GetMapping("/{memoir_id}")
    fun findMemoir(
        @Parameter(description = "회고록 ID", example = "1", required = true)
        @PathVariable("memoir_id") memoirId: Long,
    ): Response<MemoirTagResponse> {
        return Response.success(memoirTagFacade.findMemoirTagBy(memoirId))
    }

    @ApiDoc(
        summary = "회고 수정",
        description = "회고의 내용을 수정한다",
        success = "memoirTag.success.response",
        error = "memoirTag.not.found.error.response",
    )
    @PatchMapping("/modify")
    fun modifyMemoir(
        @RequestBody @Valid modifyMemoirDto: ModifyMemoirDto,
    ): Response<MemoirTagResponse> {
        return Response.success(memoirTagFacade.modifyMemoirTag(modifyMemoirDto))
    }

    @ApiDoc(
        summary = "회고 삭제",
        description = "회고를 삭제한다",
        success = "memoir.delete.success.response",
        error = "memoirTag.not.found.error.response",
    )
    @PatchMapping("/{memoir_id}")
    fun deleteMemoir(
        @Parameter(description = "회고록 ID", example = "1", required = true)
        @PathVariable("memoir_id") memoirId: Long,
    ): Response<MemoirResponse> {
        return Response.success(memoirTagFacade.deleteMemoirTagBy(memoirId))
    }
}
