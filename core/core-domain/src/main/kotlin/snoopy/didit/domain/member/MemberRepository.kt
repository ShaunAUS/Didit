package snoopy.didit.domain.member

import snoopy.didit.domain.member.dto.SignUpDto

interface MemberRepository {
    fun findAll(): List<Member>

    fun save(
        signUpDto: SignUpDto,
        encodedPassword: String,
    ): Member

    fun checkExistEmail(email: String): Member?

    fun find(memberId: Long): Member

    fun findByEmail(email: String): Member
}
