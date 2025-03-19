package snoopy.didit.space

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.space.QSpace.space
import snoopy.didit.domain.space.Space
import snoopy.didit.domain.space.SpaceRepository
import snoopy.didit.domain.space.dto.SpaceCreateDto
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Repository
class SpaceRepositoryImpl(
    private val spaceJpaRepository: SpaceJpaRepository,
    private val query: JPAQueryFactory,
) : SpaceRepository {
    override fun createSpace(spaceCreateDto: SpaceCreateDto): Space {
        return spaceJpaRepository.save(Space.of(spaceCreateDto))
    }

    override fun save(space: Space): Space {
        return spaceJpaRepository.save(space)
    }

    override fun findByMemberId(memberId: Long): List<Space> {
        return query.selectFrom(space)
            .where(space.memberId.eq(memberId).and(space.deleteFlag.eq(false)))
            .fetch()
    }

    override fun findById(spaceId: Long): Space {
        return query.selectFrom(space)
            .where(space.id.eq(spaceId).and(space.deleteFlag.eq(false)))
            .fetchOne() ?: throw BusinessException(ErrorCode.NOT_FOUND_SPACE)
    }
}
