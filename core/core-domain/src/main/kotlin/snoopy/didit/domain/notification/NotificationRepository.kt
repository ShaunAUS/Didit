package snoopy.didit.domain.notification

import snoopy.didit.domain.notification.dto.NotificationCreateDto

interface NotificationRepository {
    fun createNotification(notificationCreateDto: NotificationCreateDto): Notification

    fun findById(notificationId: Long): Notification
}
