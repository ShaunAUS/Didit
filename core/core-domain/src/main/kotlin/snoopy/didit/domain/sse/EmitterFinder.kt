package snoopy.didit.domain.sse

import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class EmitterFinder(
    private val emitterRepository: EmitterRepository,
) {
    fun findAllEventCacheStartWithByMemberId(toString: String): Map<String, Any> {
        return emitterRepository.findAllEventCacheStartWithByMemberId(toString)
    }

    fun findAllEmitterStartWithByMemberId(toString: String): Map<String, SseEmitter> {
        return emitterRepository.findAllEmitterStartWithByMemberId(toString)
    }
}
