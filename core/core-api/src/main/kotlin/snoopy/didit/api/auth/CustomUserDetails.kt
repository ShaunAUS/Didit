package snoopy.didit.api.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class CustomUserDetails(
    private val userId: Long,
    private val email: String = "",
    private val password: String = "",
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        Collections.singletonList(SimpleGrantedAuthority("USER")) // TODO: 임시로 구현

    fun getId(): Long = userId

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
