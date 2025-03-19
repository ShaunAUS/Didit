package snoopy.didit.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity

@Entity
class Member(
    email: String,
    password: String,
    name: String,
    nickName: String,
    jobFamily: Int,
    jobRole: Int,
) : BaseEntity() {
    @Column(nullable = false, length = 50)
    var email: String = email
        protected set

    @Column(nullable = false, length = 100)
    var password: String = password
        protected set

    @Column(nullable = false, length = 30)
    var name: String = name
        protected set

    @Column(nullable = false, length = 50)
    var nickName: String = nickName
        protected set

    @Column(nullable = false, length = 1)
    var jobFamily: Int = jobFamily
        protected set

    @Column(nullable = false, length = 1)
    var jobRole: Int = jobRole
        protected set

    fun updatePassword(password: String) {
        this.password = password
    }
}
