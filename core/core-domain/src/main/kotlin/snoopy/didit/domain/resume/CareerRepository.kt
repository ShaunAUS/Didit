package snoopy.didit.domain.resume

interface CareerRepository {
    fun save(career: Career): Career

    fun update(career: Career)

    fun find(careerId: Long): Career
}
