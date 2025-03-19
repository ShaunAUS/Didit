package snoopy.didit.alarm

import org.springframework.data.jpa.repository.JpaRepository
import snoopy.didit.domain.alarm.Alarm

interface AlarmJpaRepository : JpaRepository<Alarm, Long>
