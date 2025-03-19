package snoopy.didit.memberskill

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.memberskill.MemberSkillRepository
import snoopy.didit.domain.memberskill.dto.toMemberSkillEntity
import snoopy.didit.skill.Skill

@Repository
class MemberSkillRepositoryImpl(
    private val memberSkillJpaRepository: MemberSkillJpaRepository,
    private val query: JPAQueryFactory,
) : MemberSkillRepository {
    override fun saveAll(
        memberSkills: List<Skill>,
        memberId: Long,
    ) {
        memberSkills
            .forEach { memberSkillJpaRepository.save(toMemberSkillEntity(it, memberId)) }
    }
}
