package snoopy.didit.api.sse

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.api.auth.annotation.UserId
import snoopy.didit.domain.sse.NotificationEmitterFacade

@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationEmitterFacade: NotificationEmitterFacade,
) {
    @GetMapping("/subscribe", produces = ["text/event-stream;charset=UTF-8"])
    fun subscribe(
        @UserId memberId: Long,
        @RequestHeader(value = "last-Event-ID", required = false, defaultValue = "") lastEventId: String,
    ): SseEmitter {
        return notificationEmitterFacade.subscribe(memberId, lastEventId)
    }

    @PatchMapping("/read/{notification_id}")
    fun readNotification(
        @RequestParam("emitter_id") emitterId: String,
        @PathVariable("notification_id") notificationId: Long,
    ) {
        notificationEmitterFacade.readNotification(emitterId, notificationId)
    }
}
