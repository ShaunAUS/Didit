package snoopy.didit.notification

import snoopy.didit.domain.notification.dto.NotificationCreateDto
import snoopy.didit.sse.NotificationType

internal class NotificationTestFixture {
    companion object {
        fun getCreateNotificationDto(): NotificationCreateDto {
            return NotificationCreateDto.of(
                receiverId = 1,
                notificationType = NotificationType.REVIEW,
                content = "content",
                url = "url",
            )
        }
    }
}
