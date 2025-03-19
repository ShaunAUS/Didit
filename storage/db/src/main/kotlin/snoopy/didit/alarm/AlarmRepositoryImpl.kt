package snoopy.didit.alarm

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.alarm.Alarm
import snoopy.didit.domain.alarm.AlarmRepository
import snoopy.didit.domain.alarm.QAlarm.alarm
import snoopy.didit.domain.alarm.dto.AlarmCreateDto
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode
import java.time.LocalTime

@Repository
class AlarmRepositoryImpl(
    private val alarmJpaRepository: AlarmJpaRepository,
    private val query: JPAQueryFactory,
) : AlarmRepository {
    override fun createAlarm(
        alarmCreateDto: AlarmCreateDto,
        twentyFourHourTime: LocalTime,
        memberId: Long,
    ): Alarm {
        return alarmJpaRepository.save(Alarm.of(alarmCreateDto, twentyFourHourTime, memberId))
    }

    override fun findMyAlarmBy(memberId: Long): Alarm {
        return query.selectFrom(alarm)
            .where(alarm.memberId.eq(memberId))
            .fetchOne() ?: throw BusinessException(ErrorCode.NOT_FOUND_ALARM)
    }

    override fun save(alarm: Alarm) {
        alarmJpaRepository.save(alarm)
    }
}
