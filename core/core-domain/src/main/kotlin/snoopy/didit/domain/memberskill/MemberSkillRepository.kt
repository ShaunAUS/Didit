package snoopy.didit.domain.memberskill

import snoopy.didit.skill.Skill

interface MemberSkillRepository {
    fun saveAll(
        memberSkills: List<Skill>,
        memberId: Long,
    )
}
