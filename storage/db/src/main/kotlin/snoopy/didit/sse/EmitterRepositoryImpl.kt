package snoopy.didit.sse

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import snoopy.didit.domain.sse.EmitterRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

@Repository
class EmitterRepositoryImpl : EmitterRepository {
    private val emitters: MutableMap<String, SseEmitter> = ConcurrentHashMap()
    private val eventCache: MutableMap<String, Any> = ConcurrentHashMap()

    override fun save(
        emitterId: String,
        sseEmitter: SseEmitter,
    ): SseEmitter {
        emitters[emitterId] = sseEmitter
        return sseEmitter
    }

    override fun saveInEventCache(
        emitterId: String,
        event: Any,
    ) {
        eventCache[emitterId] = event
    }

    override fun findAllEmitterStartWithByMemberId(memberId: String): Map<String, SseEmitter> {
        return emitters.entries.stream()
            .filter { it.key.startsWith(memberId) }
            .collect(
                Collectors.toMap(
                    { it.key },
                    { it.value },
                ),
            )
    }

    override fun findAllEventCacheStartWithByMemberId(memberId: String): Map<String, Any> {
        return eventCache.entries.stream()
            .filter { it.key.startsWith(memberId) }
            .collect(
                Collectors.toMap(
                    { it.key },
                    { it.value },
                ),
            )
    }

    override fun deleteById(id: String) {
        emitters.remove(id)
    }

    override fun deleteAllEmitterStartWithId(memberId: String) {
        emitters.forEach { (key, _) ->
            if (key.startsWith(memberId)) {
                emitters.remove(key)
            }
        }
    }

    override fun deleteAllEventCacheStartWithId(memberId: String) {
        eventCache.forEach { (key, _) ->
            if (key.startsWith(memberId)) {
                eventCache.remove(key)
            }
        }
    }

    override fun deleteEventCacheById(emitterId: String) {
        eventCache.remove(emitterId)
    }
}
