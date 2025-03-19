package snoopy.didit.domain.memberskill.dto

import snoopy.didit.domain.memberskill.MemberSkill
import snoopy.didit.skill.Skill

fun toMemberSkillEntity(
    skill: Skill,
    memberId: Long,
): MemberSkill {
    return MemberSkill(
        type = Skill.toInt(skill),
        memberId = memberId,
    )
}
