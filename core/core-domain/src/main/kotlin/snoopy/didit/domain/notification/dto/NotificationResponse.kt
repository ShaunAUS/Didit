package snoopy.didit.domain.notification.dto

import snoopy.didit.domain.notification.Notification

data class NotificationResponse(
    val id: Long,
    val content: String,
    val name: String,
    val createAt: String,
) {
    companion object {
        fun of(savedNotification: Notification): NotificationResponse {
            return NotificationResponse(
                id = savedNotification.id!!,
                content = savedNotification.content!!,
                name = savedNotification.notificationType.toString(),
                createAt = savedNotification.createdDate.toString(),
            )
        }
    }
}
