package snoopy.didit.notification

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import snoopy.didit.domain.notification.Notification
import snoopy.didit.sse.NotificationType

class NotificationTest {
    // TODO 주입 문제 해결해서 Repository test 작성

    @Test
    fun `NotificationEntity_를_CreateDto로_변환한다`() {
        // given
        val createNotificationDto = NotificationTestFixture.getCreateNotificationDto()

        // when
        val result = Notification.of(createNotificationDto)

        // then
        assertThat(result.url).isEqualTo("url")
        assertThat(result.notificationType).isEqualTo(NotificationType.toInt(NotificationType.REVIEW))
        assertThat(result.receiverId).isEqualTo(1)
        assertThat(result.content).isEqualTo("content")
    }
}
