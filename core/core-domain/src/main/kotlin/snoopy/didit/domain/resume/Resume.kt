package snoopy.didit.domain.resume

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.resume.dto.ResumeUpdateRequest

@Entity
@Table(name = "resume")
class Resume(
    phoneNumber: String? = null,
    email: String,
    oneLineIntroduction: String? = null,
    memberId: Long,
) : BaseEntity() {
    @Column(nullable = false, length = 11)
    var phoneNumber: String? = phoneNumber
        protected set

    @Column(nullable = false, length = 50)
    var email: String = email
        protected set

    @Column
    var oneLineIntroduction: String? = oneLineIntroduction
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    fun update(request: ResumeUpdateRequest) {
        this.phoneNumber = request.phoneNumber
        this.email = request.email
        this.oneLineIntroduction = request.introduction
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Resume) return false

        if (memberId != other.memberId) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = memberId.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + (oneLineIntroduction?.hashCode() ?: 0)
        return result
    }
}
