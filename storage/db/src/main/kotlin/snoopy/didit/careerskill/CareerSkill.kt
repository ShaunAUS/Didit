package snoopy.didit.careerskill

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class CareerSkill(
    type: Int,
    careerId: Long,
) {
    @Column(nullable = false, length = 1)
    var type: Int = type
        protected set

    @Column(nullable = false)
    var careerId: Long = careerId
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
