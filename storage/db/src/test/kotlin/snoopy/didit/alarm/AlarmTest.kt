package snoopy.didit.alarm

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import snoopy.didit.domain.alarm.Alarm
import java.time.LocalTime

class AlarmTest {
    // TODO 주입 문제 해결해서 Repository test 작성

    @Test
    fun `CreateAlarmDto을 Alarm엔티티로 변환한다`() {
        val createAlarmDto = AlarmTestFixture.getAlarmCreateDto()
        val twentyFourHourTime = LocalTime.of(22, 0)

        val result = Alarm.of(createAlarmDto, twentyFourHourTime, 1)

        assertThat(result.time).isEqualTo(LocalTime.of(22, 0))
        assertThat(result.isAlarmEnable).isTrue()
        assertThat(result.memberId).isEqualTo(1)
    }

    @Test
    fun `알람 받기 여부를 수정한다`() {
        val alarm = AlarmTestFixture.getAlarm()
        alarm.updateAlarmTime(LocalTime.of(23, 0))
        assertThat(alarm.time).isEqualTo(LocalTime.of(23, 0))
    }

    @Test
    fun `알람 시간을 수정한다`() {
        val alarm = AlarmTestFixture.getAlarm()
        alarm.updateAlarmEnable(false)
        assertThat(alarm.isAlarmEnable).isFalse()
    }
}
