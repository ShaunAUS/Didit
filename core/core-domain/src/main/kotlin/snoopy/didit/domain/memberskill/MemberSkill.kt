package snoopy.didit.domain.memberskill

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MemberSkill(
    type: Int,
    memberId: Long,
) {
    @Column(nullable = false, length = 1)
    var type: Int = type
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
