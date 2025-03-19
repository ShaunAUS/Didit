package snoopy.didit.domain.notification

import org.springframework.stereotype.Component

@Component
class NotificationFinder(
    private val notificationRepository: NotificationRepository,
) {
    fun findById(notificationId: Long): Notification {
        return notificationRepository.findById(notificationId)
    }
}
