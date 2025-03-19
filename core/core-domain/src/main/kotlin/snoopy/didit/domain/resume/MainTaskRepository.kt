package snoopy.didit.domain.resume

interface MainTaskRepository {
    fun save(mainTask: MainTask)
}
