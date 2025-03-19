package snoopy.didit.notification

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.notification.Notification

// 알림 crud
interface NotificationJpaRepository : JpaRepository<Notification, Long>
