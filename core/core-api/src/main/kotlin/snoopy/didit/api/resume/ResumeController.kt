package snoopy.didit.api.resume

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.domain.resume.CareerAppender
import snoopy.didit.domain.resume.CareerProcessor
import snoopy.didit.domain.resume.MainTaskAppender
import snoopy.didit.domain.resume.ResumeAppender
import snoopy.didit.domain.resume.ResumeProcessor
import snoopy.didit.domain.resume.dto.CareerCreateDto
import snoopy.didit.domain.resume.dto.CareerResponse
import snoopy.didit.domain.resume.dto.CareerUpdateRequest
import snoopy.didit.domain.resume.dto.MainTaskRequest
import snoopy.didit.domain.resume.dto.ResumeCreateRequest
import snoopy.didit.domain.resume.dto.ResumeResponse
import snoopy.didit.domain.resume.dto.ResumeUpdateRequest
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1/resume")
class ResumeController(
    private val mainTaskAppender: MainTaskAppender,
    private val careerAppender: CareerAppender,
    private val careerProcessor: CareerProcessor,
    private val resumeAppender: ResumeAppender,
    private val resumeProcessor: ResumeProcessor,
) {
    @PostMapping("/main-task/new")
    fun createMainTask(
        @RequestBody @Valid request: MainTaskRequest,
    ): Response<Any> {
        mainTaskAppender.save(request)
        return Response.success()
    }

    @PostMapping("/career/new")
    fun createCareer(
        @RequestBody @Valid request: CareerCreateDto,
    ): Response<CareerResponse> {
        val careerResponse = careerAppender.init(request = request)
        return Response.success(careerResponse)
    }

    @PostMapping("/resume/new")
    fun createResume(
        @RequestBody @Valid request: ResumeCreateRequest,
    ): Response<ResumeResponse> {
        val resumeResponse = resumeAppender.init(request)
        return Response.success(resumeResponse)
    }

    @PutMapping("career")
    fun updateCareer(
        @RequestBody @Valid request: CareerUpdateRequest,
    ): Response<Any> {
        careerProcessor.update(request)
        return Response.success()
    }

    @PutMapping
    fun updateResume(
        @RequestBody @Valid request: ResumeUpdateRequest,
    ): Response<Any> {
        resumeProcessor.update(request)
        return Response.success()
    }
}
