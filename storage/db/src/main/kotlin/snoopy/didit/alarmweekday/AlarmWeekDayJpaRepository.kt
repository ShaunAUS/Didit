package snoopy.didit.alarmweekday

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.alarmweekday.AlarmWeekDay

interface AlarmWeekDayJpaRepository : JpaRepository<AlarmWeekDay, Long>
