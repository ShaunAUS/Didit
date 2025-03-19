package snoopy.didit.domain.member

import getOrThrow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import snoopy.didit.domain.alarm.AlarmFacade
import snoopy.didit.domain.member.dto.SignUpDto
import snoopy.didit.domain.memberskill.MemberSkillRepository

@Service
class SignUpService(
    private val memberRepository: MemberRepository,
    private val memberSkillRepository: MemberSkillRepository,
    private val alarmFacade: AlarmFacade,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(signUpDto: SignUpDto) {
        val member = memberRepository.save(signUpDto = signUpDto, encodedPassword = createEncodePassWord(signUpDto))
        signUpDto.memberSkills?.let { memberSkillRepository.saveAll(it, member.id.getOrThrow()) }
        signUpDto.alarm?.let { alarmFacade.createAlarm(it, member.id.getOrThrow()) }
    }

    private fun createEncodePassWord(signUpDto: SignUpDto) = passwordEncoder.encode(signUpDto.password)
}
