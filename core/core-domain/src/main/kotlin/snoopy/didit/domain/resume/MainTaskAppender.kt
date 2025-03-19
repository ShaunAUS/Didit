package snoopy.didit.domain.resume

import org.springframework.stereotype.Component
import snoopy.didit.domain.resume.dto.MainTaskRequest
import snoopy.didit.domain.resume.dto.toDomain

@Component
class MainTaskAppender(private val mainTaskRepository: MainTaskRepository) {
    fun save(dto: MainTaskRequest) {
        val mainTask = dto.toDomain()
        mainTaskRepository.save(mainTask)
    }
}
