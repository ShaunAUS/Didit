package snoopy.didit.domain.sse

import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.domain.notification.Notification
import snoopy.didit.domain.notification.dto.NotificationResponse
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode
import java.io.IOException

private const val DEFAULT_TIMEOUT: Long = 60L * 1000L * 60L // 1시간

@Service
class EmitterService(
    private val emitterAppender: EmitterAppender,
    private val emitterFinder: EmitterFinder,
) {
    fun subscribe(
        userId: Long,
        lastLostEventId: String,
    ): SseEmitter {
        val emitterId = makeTimeIncludeUserId(userId)
        val emitter = emitterAppender.save(emitterId, SseEmitter(DEFAULT_TIMEOUT))

        // SSE 연결이 완전히 종료될 때 실행되는 콜백, 클라이언트가 연결끊거나 서버종료시
        emitter.onCompletion { emitterAppender.deleteById(emitterId) }
        emitter.onTimeout { emitterAppender.deleteById(emitterId) }

        // eventCache 저장 X
        sendToClientByEmitter(emitter, emitterId, "EventStream Created. [userId=$userId]")

        if (hasLostData(lastLostEventId)) {
            sendLostData(lastLostEventId, userId, emitter)
        }

        return emitter
    }

    fun deleteEventCache(emitterId: String) {
        emitterAppender.deleteEventCache(emitterId)
    }

    fun saveInEventCacheAndSendToClient(
        receiverId: Long,
        savedNotification: Notification,
    ) {
        val emitters = emitterFinder.findAllEmitterStartWithByMemberId(receiverId.toString())

        // emitterId, data
        emitters.forEach { (emitterId, emitter) ->
            emitterAppender.saveInEventCache(emitterId, savedNotification)
            sendToClientByEmitter(emitter, emitterId, NotificationResponse.of(savedNotification))
        }
    }

    private fun sendToClientByEmitter(
        emitter: SseEmitter,
        emitterId: String,
        data: Any,
    ) {
        try {
            emitter.send(
                SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data(data),
            )
        } catch (exception: IOException) {
            emitterAppender.deleteById(emitterId)
            throw BusinessException(ErrorCode.SSE_CONNECTION_ERROR)
        }
    }

    private fun makeTimeIncludeUserId(userId: Long): String = "${userId}_${System.currentTimeMillis()}"

    private fun hasLostData(lastEventId: String): Boolean = lastEventId.isNotEmpty()

    private fun sendLostData(
        lastEventId: String,
        userId: Long,
        emitter: SseEmitter,
    ) {
        val eventCaches = emitterFinder.findAllEventCacheStartWithByMemberId(userId.toString())
        eventCaches.entries
            .filter { entry -> lastEventId < entry.key }
            .forEach { entry -> sendToClientByEmitter(emitter, entry.key, entry.value) }
    }
}
