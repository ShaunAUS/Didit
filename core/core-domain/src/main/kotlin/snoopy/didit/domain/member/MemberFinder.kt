package snoopy.didit.domain.member

import org.springframework.stereotype.Component
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Component
class MemberFinder(
    private val memberRepository: MemberRepository,
) {
    fun findAllMembers(): List<Member> = memberRepository.findAll()

    fun findByEmail(email: String): Member {
        return memberRepository.findByEmail(email)
    }

    fun checkExistEmail(email: String) {
        memberRepository.checkExistEmail(email)?.let {
            throw BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS)
        }
    }

    fun find(memberId: Long): Member {
        val member = memberRepository.find(memberId)
        return member
    }
}
