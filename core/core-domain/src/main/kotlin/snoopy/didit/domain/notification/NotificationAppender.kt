package snoopy.didit.domain.notification

import org.springframework.stereotype.Component
import snoopy.didit.domain.notification.dto.NotificationCreateDto

@Component
class NotificationAppender(
    private val notificationRepository: NotificationRepository,
) {
    fun createNotification(notificationCreateDto: NotificationCreateDto): Notification {
        return notificationRepository.createNotification(notificationCreateDto)
    }
}
