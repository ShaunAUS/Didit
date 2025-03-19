package snoopy.didit.api.auth

import getOrThrow
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import snoopy.didit.domain.member.MemberRepository

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails = createUserDetails(email = email)

    private fun createUserDetails(email: String): UserDetails {
        val foundMember = memberRepository.findByEmail(email = email)

        val customUserDetails = CustomUserDetails(
            userId = foundMember.id.getOrThrow(),
            email = foundMember.email,
            password = foundMember.password,
            authorities = listOf(SimpleGrantedAuthority("ROLE_USER")), //TODO: 임시로 구현
        )

        return customUserDetails
    }
}
