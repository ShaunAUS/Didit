package snoopy.didit.career

import org.springframework.stereotype.Repository
import snoopy.didit.domain.resume.Career
import snoopy.didit.domain.resume.CareerRepository
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Repository
class CareerRepositoryImpl(
    private val careerJpaRepository: CareerJpaRepository,
) : CareerRepository {
    override fun save(career: Career): Career {
        val savedCareer = careerJpaRepository.save(career)
        return savedCareer
    }

    override fun update(career: Career) {
        careerJpaRepository.save(career)
    }

    override fun find(careerId: Long): Career =
        careerJpaRepository.findById(careerId).orElseThrow {
            BusinessException(ErrorCode.NOT_FOUND_CAREER)
        }
}
