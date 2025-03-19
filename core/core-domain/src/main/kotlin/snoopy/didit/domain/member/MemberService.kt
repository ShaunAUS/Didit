package snoopy.didit.domain.member

import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberFinder: MemberFinder,
    private val memberAppender: MemberAppender,
) {
    fun findAllMembers(): List<Member> {
        return memberFinder.findAllMembers()
    }

    fun findByEmail(email: String): Member {
        return memberFinder.findByEmail(email)
    }
}
