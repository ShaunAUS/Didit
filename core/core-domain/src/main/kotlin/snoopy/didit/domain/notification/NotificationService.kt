package snoopy.didit.domain.notification

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.notification.dto.NotificationCreateDto

@Service
class NotificationService(
    private val notificationAppender: NotificationAppender,
    private val notificationFinder: NotificationFinder,
) {
    fun createNotification(notificationCreateDto: NotificationCreateDto): Notification {
        return notificationAppender.createNotification(notificationCreateDto)
    }

    @Transactional
    fun readNotification(notificationId: Long) {
        val notification = notificationFinder.findById(notificationId)
        notification.updateIsRead(true)
    }
}
