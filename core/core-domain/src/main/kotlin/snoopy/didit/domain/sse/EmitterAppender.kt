package snoopy.didit.domain.sse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.domain.notification.Notification

@Component
class EmitterAppender(
    private val emitterRepository: EmitterRepository,
) {
    fun save(
        emitterId: String,
        sseEmitter: SseEmitter,
    ): SseEmitter {
        return emitterRepository.save(emitterId, sseEmitter)
    }

    fun deleteById(emitterId: String) {
        emitterRepository.deleteById(emitterId)
    }

    fun deleteEventCache(emitterId: String) {
        emitterRepository.deleteEventCacheById(emitterId)
    }

    fun saveInEventCache(
        emitterId: String,
        savedNotification: Notification,
    ) {
        return emitterRepository.saveInEventCache(emitterId, savedNotification)
    }
}
