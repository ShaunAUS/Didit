package snoopy.didit.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver
import snoopy.didit.ratelimit.RateLimitService
import snoopy.didit.security.filter.CustomAccessDeniedHandler
import snoopy.didit.security.filter.JwtExceptionFilter
import snoopy.didit.security.filter.JwtFilter
import snoopy.didit.security.filter.RateLimitFilter
import snoopy.didit.security.token.TokenExtractor
import snoopy.didit.security.token.TokenValidator
import snoopy.didit.security.webmvc.interceptor.config.RateLimitPathProvider

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenValidator: TokenValidator,
    private val tokenExtractor: TokenExtractor,
    private val permitAllPathProvider: PermitAllPathProvider,
    private val rateLimitPathProvider: RateLimitPathProvider,
    private val rateLimitService: RateLimitService,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .cors { it.configurationSource(cors()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        *permitAllPathProvider.publicPaths,
                    ).permitAll()
                    .requestMatchers(
                        "/api/v1/auth/authenticate",
                    ).hasRole("USER") // TODO: 임시로 구현해놓은 상태
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(
                JwtFilter(tokenValidator, tokenExtractor, permitAllPathProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            ).addFilterBefore(
                JwtExceptionFilter(),
                JwtFilter::class.java,
            ).addFilterBefore(
                RateLimitFilter(rateLimitService, rateLimitPathProvider, handlerExceptionResolver),
                JwtExceptionFilter::class.java,
            ).exceptionHandling { it.accessDeniedHandler(CustomAccessDeniedHandler()) }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun cors(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:3000", "https://front.d-dit.kr")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
