package snoopy.didit.domain.notification.dto

import snoopy.didit.sse.NotificationType

data class NotificationCreateDto(
    val receiverId: Long,
    val notificationType: NotificationType,
    val content: String,
    val url: String,
) {
    companion object {
        fun of(
            receiverId: Long,
            notificationType: NotificationType,
            content: String,
            url: String,
        ): NotificationCreateDto {
            return NotificationCreateDto(
                receiverId = receiverId,
                notificationType = notificationType,
                content = content,
                url = url,
            )
        }
    }
}
