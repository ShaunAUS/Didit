package snoopy.didit.notification

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.notification.Notification
import snoopy.didit.domain.notification.NotificationRepository
import snoopy.didit.domain.notification.dto.NotificationCreateDto
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Repository
class NotificationRepositoryImpl(
    private val notificationJpaRepository: NotificationJpaRepository,
    private val query: JPAQueryFactory,
) : NotificationRepository {
    override fun createNotification(notificationCreateDto: NotificationCreateDto): Notification {
        return notificationJpaRepository.save(Notification.of(notificationCreateDto))
    }

    override fun findById(notificationId: Long): Notification {
        return notificationJpaRepository.findById(notificationId)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_FOUND_NOTIFICATION) }
    }
}
