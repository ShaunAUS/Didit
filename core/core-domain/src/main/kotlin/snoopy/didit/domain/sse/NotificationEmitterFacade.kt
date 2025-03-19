package snoopy.didit.domain.sse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.domain.notification.NotificationService
import snoopy.didit.domain.notification.dto.NotificationCreateDto
import snoopy.didit.sse.NotificationType

@Component
class NotificationEmitterFacade(
    private val emitterService: EmitterService,
    private val notificationService: NotificationService,
) {
    fun subscribe(
        memberId: Long,
        lastEventId: String,
    ): SseEmitter {
        return emitterService.subscribe(memberId, lastEventId)
    }

    fun readNotification(
        emitterId: String,
        notificationId: Long,
    ) {
        notificationService.readNotification(notificationId)
        emitterService.deleteEventCache(emitterId)
    }

    fun sendAlarm(
        receiverId: Long,
        notificationType: NotificationType,
        content: String,
        url: String,
    ) {
        val savedNotification =
            notificationService.createNotification(
                NotificationCreateDto.of(receiverId, notificationType, content, url),
            )
        emitterService.saveInEventCacheAndSendToClient(receiverId, savedNotification)
    }
}
