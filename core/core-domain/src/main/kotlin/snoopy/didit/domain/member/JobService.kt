package snoopy.didit.domain.member

import org.springframework.stereotype.Service
import snoopy.didit.domain.member.dto.JobFamilyDto
import snoopy.didit.domain.member.dto.toDto
import snoopy.didit.member.JobRole

@Service
class JobService {
    fun getJobs(): List<JobFamilyDto> {
        val groupedJobs = JobRole.entries.groupBy { it.jobFamily }

        return groupedJobs.map { (jobFamily, roles) ->
            JobFamilyDto(
                jobFamilyNumber = jobFamily.number,
                jobFamilyName = jobFamily.familyName,
                jobRoles = roles.map { it.toDto() },
            )
        }
    }
}
