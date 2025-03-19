package snoopy.didit.sse

enum class NotificationType(
    val notificationName: String,
    val number: Int,
) {
    REVIEW("리뷰 알림", 0),
    CHAT("채팅 알림", 1),
    ;

    companion object {
        fun toType(number: Int): NotificationType {
            return entries.first { it.number == number }
        }

        fun toInt(notificationType: NotificationType): Int {
            return notificationType.number
        }
    }
}
