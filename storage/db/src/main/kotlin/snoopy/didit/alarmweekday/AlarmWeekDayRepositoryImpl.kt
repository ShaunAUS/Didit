package snoopy.didit.alarmweekday

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.alarmweekday.AlarmWeekDay
import snoopy.didit.domain.alarmweekday.AlarmWeekDayRepository
import snoopy.didit.domain.alarmweekday.QAlarmWeekDay.alarmWeekDay
import snoopy.didit.memoir.Weekday

@Repository
class AlarmWeekDayRepositoryImpl(
    private val alarmWeekDayJpaRepository: AlarmWeekDayJpaRepository,
    private val query: JPAQueryFactory,
) : AlarmWeekDayRepository {
    override fun createAlarmWeekDay(
        weekDay: List<Weekday>,
        alarmId: Long,
    ): List<AlarmWeekDay> {
        return weekDay.map { weekDay -> alarmWeekDayJpaRepository.save(AlarmWeekDay.of(weekDay, alarmId)) }
    }

    override fun findByAlarmId(alarmId: Long): List<AlarmWeekDay> {
        return query.selectFrom(alarmWeekDay)
            .where(alarmWeekDay.alarmId.eq(alarmId))
            .fetch()
    }

    override fun deleteByAlarmId(alarmId: Long) {
        query.delete(alarmWeekDay)
            .where(alarmWeekDay.alarmId.eq(alarmId))
            .execute()
    }
}
