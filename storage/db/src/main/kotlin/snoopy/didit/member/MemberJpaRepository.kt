package snoopy.didit.member

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.member.Member

interface MemberJpaRepository : JpaRepository<Member, Long>
