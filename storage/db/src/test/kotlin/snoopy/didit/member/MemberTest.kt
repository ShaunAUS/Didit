package snoopy.didit.member

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

const val TEMP_PASSWORD = "tempPassword"

class MemberTest {
    // TODO 주입 문제 해결해서 Repository test 작성

    @Test
    fun `member 비밀번호를 변경 한다`() {
        val member = MemberTestFixture.getMember()
        member.updatePassword(TEMP_PASSWORD)

        assertThat(member.password).isEqualTo(TEMP_PASSWORD)
    }
}
