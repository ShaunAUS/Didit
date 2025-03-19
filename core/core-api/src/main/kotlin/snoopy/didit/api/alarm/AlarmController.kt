package snoopy.didit.api.alarm

import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.api.auth.annotation.UserId
import snoopy.didit.domain.alarm.AlarmFacade
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.domain.alarm.dto.AlarmResponse
import snoopy.didit.domain.alarm.dto.AlarmUpdateDto
import snoopy.didit.swagger.annotation.ApiDoc
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1/alarm")
class AlarmController(
    private val alarmFacade: AlarmFacade,
) {
    @ApiDoc(
        summary = "알람 생성",
        description = "유저의 알람을 생성한다",
        success = "alarm.success.response",
        error = "alarm.valid.error.response",
    )
    @PostMapping
    fun createAlarm(
        @UserId memberId: Long,
        @RequestBody @Valid alarmCreateDto: AlarmCreateDto,
    ): Response<AlarmResponse> {
        return Response.success(alarmFacade.createAlarm(alarmCreateDto, memberId))
    }

    @ApiDoc(
        summary = "알람 조회",
        description = "유저의 알람을 조회한다",
        success = "alarm.success.response",
        error = "alarm.not.found.error.response",
    )
    @GetMapping
    fun findMyAlarmInfo(
        @UserId memberId: Long,
    ): Response<AlarmResponse> {
        return Response.success(alarmFacade.findMyAlarmInfo(memberId))
    }

    @ApiDoc(
        summary = "이메일 알람 활성화 여부 수정",
        description = "이메일 알람 활성화 여부를 수정 한다",
        success = "alarm.enable.success.response",
        error = "alarm.not.found.error.response",
    )
    @PatchMapping("/enable")
    fun updateEmailAlarmEnable(
        @UserId memberId: Long,
        @Parameter(description = "알람 활성화 여부", example = "true", required = true)
        @RequestParam("is_alarm_enable") isAlarmEnable: Boolean,
    ): Response<Any> {
        alarmFacade.updateEmailAlarmEnable(isAlarmEnable, memberId)
        return Response.success()
    }

    @ApiDoc(
        summary = "알람 수정",
        description = "유저의 알람을 수정한다",
        success = "alarm.success.response",
        error = "alarm.not.found.error.response",
    )
    @PatchMapping
    fun updateAlarm(
        @UserId memberId: Long,
        @RequestBody alarmUpdateDto: AlarmUpdateDto,
    ): Response<AlarmResponse> {
        return Response.success(alarmFacade.updateAlarm(alarmUpdateDto, memberId))
    }
}
