package snoopy.didit.domain.resume

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.resume.dto.CareerUpdateRequest
import java.time.LocalDateTime

@Entity
class Career(
    company: String? = null,
    department: String? = null,
    position: String? = null,
    workStartDate: LocalDateTime? = null,
    workFinishDate: LocalDateTime? = null,
    isWorking: Boolean? = null,
    memberId: Long,
    resumeId: Long,
) : BaseEntity() {
    @Column(nullable = false, length = 20)
    var company: String? = company
        protected set

    @Column(nullable = false, length = 20)
    var department: String? = department
        protected set

    @Column(nullable = false, length = 20)
    var position: String? = position
        protected set

    @Column(nullable = false)
    var workStartDate: LocalDateTime? = workStartDate
        protected set

    @Column(nullable = false)
    var workFinishDate: LocalDateTime? = workFinishDate
        protected set

    @Column(nullable = false)
    var isWorking: Boolean? = isWorking
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    @Column(nullable = false)
    var resumeId: Long = resumeId
        protected set

    fun updateId(id: Long) {
        this.id = id
    }

    fun update(request: CareerUpdateRequest) {
        company = request.companyName
        department = request.departmentName
        position = request.positionName
        workStartDate = request.workStartDate
        workFinishDate = request.workFinishDate
        isWorking = request.isWorking
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Career) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
