package snoopy.didit.domain.resume

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import snoopy.didit.domain.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "main_task")
class MainTask(
    workStartDate: LocalDateTime? = null,
    workFinishDate: LocalDateTime? = null,
    achievement: String,
    explanation: String,
    careerId: Long,
) : BaseEntity() {
    init {
        require(achievement.isNotBlank()) { "achievement must not be blank" }
        require(explanation.isNotBlank()) { "explanation must not be blank" }
        require(careerId > 0L) { "careerId must be positive" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MainTask) return false

        if (careerId != other.careerId) return false
        if (workStartDate != other.workStartDate) return false
        if (workFinishDate != other.workFinishDate) return false
        if (achievement != other.achievement) return false
        if (explanation != other.explanation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = careerId.hashCode()
        result = 31 * result + (workStartDate?.hashCode() ?: 0)
        result = 31 * result + (workFinishDate?.hashCode() ?: 0)
        result = 31 * result + achievement.hashCode()
        result = 31 * result + explanation.hashCode()
        return result
    }

    @Column(nullable = true)
    var workStartDate: LocalDateTime? = workStartDate
        protected set

    @Column(nullable = true)
    var workFinishDate: LocalDateTime? = workFinishDate
        protected set

    @Column(nullable = false, length = 100)
    var achievement: String = achievement
        protected set

    @Column(columnDefinition = "TEXT")
    var explanation: String = explanation
        protected set

    @Column(nullable = false)
    var careerId: Long = careerId
        protected set
}
