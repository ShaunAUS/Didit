package snoopy.didit.domain.sse

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface EmitterRepository {
    fun save(
        emitterId: String,
        sseEmitter: SseEmitter,
    ): SseEmitter

    fun saveInEventCache(
        eventCacheId: String,
        event: Any,
    )

    fun findAllEmitterStartWithByMemberId(memberId: String): Map<String, SseEmitter>

    fun findAllEventCacheStartWithByMemberId(memberId: String): Map<String, Any>

    fun deleteById(id: String)

    fun deleteAllEmitterStartWithId(memberId: String)

    fun deleteAllEventCacheStartWithId(memberId: String)

    fun deleteEventCacheById(eventId: String)
}
