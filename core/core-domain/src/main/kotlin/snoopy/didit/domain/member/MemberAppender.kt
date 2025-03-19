package snoopy.didit.domain.member

import org.springframework.stereotype.Component

@Component
class MemberAppender(
    private val memberRepository: MemberRepository,
)
