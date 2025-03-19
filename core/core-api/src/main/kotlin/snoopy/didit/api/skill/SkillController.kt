package snoopy.didit.api.skill

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.domain.memberskill.dto.SkillResponse
import snoopy.didit.skill.Skill
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1")
class SkillController {
    @GetMapping("/skills")
    fun getAllSkills(): Response<List<SkillResponse>> {
        val skills = Skill.getAll().map { SkillResponse(it.first, it.second) }
        return Response.success(skills)
    }
}
