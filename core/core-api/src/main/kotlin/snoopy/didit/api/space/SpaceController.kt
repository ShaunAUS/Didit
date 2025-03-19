package snoopy.didit.api.space

import io.github.oshai.kotlinlogging.KotlinLogging
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.api.auth.annotation.UserId
import snoopy.didit.domain.space.SpaceService
import snoopy.didit.domain.space.dto.SpaceCreateDto
import snoopy.didit.domain.space.dto.SpaceResponse
import snoopy.didit.domain.space.dto.SpaceUpdateDto
import snoopy.didit.swagger.annotation.ApiDoc
import snoopy.didit.template.Response

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/space")
class SpaceController(
    private val spaceService: SpaceService,
) {
    @ApiDoc(
        summary = "공간 생성",
        description = "유저가 공간을 생성한다",
        success = "space.success.response",
        error = "space.valid.error.response",
    )
    @PostMapping("")
    fun createSpace(
        @RequestBody @Valid spaceCreateDto: SpaceCreateDto,
    ): Response<SpaceResponse> {
        return Response.success(spaceService.createSpace(spaceCreateDto))
    }

    @ApiDoc(
        summary = "공간 조회",
        description = "유저가 공간을 조회한다",
        success = "space.list.success.response",
        error = "space.empty.success.response",
    )
    @GetMapping("")
    fun findSpaces(
        @UserId memberId: Long,
    ): Response<List<SpaceResponse>> {
        return Response.success(spaceService.findSpaceByMemberId(memberId))
    }

    @ApiDoc(
        summary = "공간 수정",
        description = "공간의 정보를 수정한다",
        success = "space.success.response",
        error = "space.not.found.error.response",
    )
    @PostMapping("/modify")
    fun modifySpace(
        @RequestBody @Valid spaceUpdateDto: SpaceUpdateDto,
    ): Response<SpaceResponse> {
        return Response.success(spaceService.updateSpace(spaceUpdateDto))
    }

    @ApiDoc(
        summary = "공간 삭제",
        description = "공간을 삭제한다",
        success = "space.success.response",
        error = "space.not.found.error.response",
    )
    @PatchMapping("/{space_id}/delete")
    fun deleteSpace(
        @Parameter(description = "공간 ID", example = "1", required = true)
        @PathVariable("space_id") spaceId: Long,
    ): Response<SpaceResponse> {
        return Response.success(spaceService.deleteSpace(spaceId))
    }
}
