package snoopy.didit.member

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.member.Member
import snoopy.didit.domain.member.MemberRepository
import snoopy.didit.domain.member.QMember.member
import snoopy.didit.domain.member.dto.SignUpDto
import snoopy.didit.domain.member.dto.toMemberEntity
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
    private val query: JPAQueryFactory,
) : MemberRepository {
    override fun findAll(): List<Member> =
        query
            .selectFrom(member)
            .where(member.deleteFlag.eq(false))
            .fetch()

    override fun findByEmail(email: String): Member {
        return query.selectFrom(member)
            .where(member.email.eq(email).and(member.deleteFlag.eq(false)))
            .fetchOne() ?: throw BusinessException(ErrorCode.NOT_FOUND_MEMBER)
    }

    override fun save(
        signUpDto: SignUpDto,
        encodedPassword: String,
    ): Member {
        val member = signUpDto.toMemberEntity(encodedPassword)
        return memberJpaRepository.save(member)
    }

    override fun checkExistEmail(email: String): Member? =
        query
            .selectFrom(member)
            .where(member.email.eq(email))
            .fetchOne()

    override fun find(memberId: Long): Member =
        memberJpaRepository
            .findById(memberId)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_FOUND_MEMBER) }
}
