package snoopy.didit.maintask

import org.springframework.stereotype.Repository
import snoopy.didit.domain.resume.MainTask
import snoopy.didit.domain.resume.MainTaskRepository

@Repository
class MainTaskRepositoryImpl(private val mainTaskJpaRepository: MainTaskJpaRepository) : MainTaskRepository {
    override fun save(mainTask: MainTask) {
        mainTaskJpaRepository.save(mainTask)
    }
}
