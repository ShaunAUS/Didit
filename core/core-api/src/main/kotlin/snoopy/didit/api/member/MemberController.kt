package snoopy.didit.api.member

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.domain.member.JobService
import snoopy.didit.domain.member.MemberService
import snoopy.didit.domain.member.dto.JobFamilyDto
import snoopy.didit.domain.memoir.dto.MemoirTagWithDateResponse
import snoopy.didit.domain.memoir.dto.MemoirWithSpaceResponse
import snoopy.didit.domain.memoritag.MemoirTagFacade
import snoopy.didit.domain.memoritag.dto.MemoirContinousWritingCalculateRequestDto
import snoopy.didit.domain.memoritag.dto.MemoirContinuousWritingResponse
import snoopy.didit.memoir.Calendar
import snoopy.didit.swagger.annotation.ApiDoc
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService,
    private val memoirTagFacade: MemoirTagFacade,
    private val jobService: JobService,
) {
    @ApiDoc(
        summary = "전체 스페이스별 회고록 조회",
        description = "로그인한 유저의 회고록을 스페이스별로  조회한다",
        success = "member.memoirTag.list.success.response",
        error = "member.memoirTag.empty.success.response",
    )
    @GetMapping("/{member_id}/space/memoirs")
    fun findMemoirByMemberId(
        @Parameter(description = "회원 ID", example = "1", required = true)
        @PathVariable("member_id") memberId: Long,
    ): Response<List<MemoirWithSpaceResponse>> {
        return Response.success(memoirTagFacade.findMemoirTagsBy(memberId))
    }

    @ApiDoc(
        summary = "전체 스페이스 년, 월로 조회",
        description = "로그인한 유저의 회고록을 공간, 년, 월로 조회한다",
        success = "member.memoirTag.date.success.response",
        error = "member.memoirTag.date.empty.success.response",
    )
    @GetMapping("/{member_id}/date/memoirs")
    fun findMemoirByYearAndMonth(
        @Parameter(description = "회원 ID", example = "1", required = true)
        @PathVariable("member_id") memberId: Long,
        @Parameter(description = "년도", example = "2021", required = true)
        @RequestParam("year") year: Int,
        @RequestParam("month") month: Calendar,
    ): Response<List<MemoirTagWithDateResponse>> {
        return Response.success(memoirTagFacade.findMemoirTagsByDate(memberId, year, month))
    }

    @ApiDoc(
        summary = "연속 글쓴 날자를 계산",
        description = "유저가 연속을 글쓴 날짜와, 연속일 수 ,랜덤메세지를 반환한다 ",
        success = "member.memoirTag.continous.writing.success.response",
        error = "member.memoirTag.continous.writing.error.response",
    )
    @GetMapping("/{member_id}/calendar/memoirs")
    fun calculateContinuousWritingMemoirTag(
        @Parameter(description = "회원 ID", example = "1", required = true)
        @PathVariable("member_id") memberId: Long,
        @Parameter(description = "년도", example = "2021", required = true)
        @RequestParam("year") year: Int,
        @RequestParam("month") calendar: Calendar,
    ): Response<MemoirContinuousWritingResponse> {
        val memoirCalendarRequest = MemoirContinousWritingCalculateRequestDto.of(year, calendar)
        return Response.success(
            memoirTagFacade.calculateContinuousWritingMemoirTag(memberId, memoirCalendarRequest),
        )
    }

    @GetMapping("/jobs")
    fun getJobs(): Response<List<JobFamilyDto>> {
        return Response.success(jobService.getJobs())
    }
}
