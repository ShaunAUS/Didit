package snoopy.didit.domain.notification

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.notification.dto.NotificationCreateDto

@Entity
class Notification(
    @Column(nullable = false)
    val content: String,
    @Column(nullable = false)
    val url: String,
    isRead: Boolean,
    @Column(nullable = false)
    val notificationType: Int,
    @Column(nullable = false)
    val receiverId: Long,
) : BaseEntity() {
    @Column(nullable = false)
    var isRead: Boolean = isRead
        protected set

    fun updateIsRead(isRead: Boolean) {
        this.isRead = isRead
    }

    companion object {
        fun of(notificationCreateDto: NotificationCreateDto): Notification {
            return Notification(
                content = notificationCreateDto.content,
                url = notificationCreateDto.url,
                notificationType = notificationCreateDto.notificationType.ordinal,
                receiverId = notificationCreateDto.receiverId,
                isRead = false,
            )
        }
    }
}
