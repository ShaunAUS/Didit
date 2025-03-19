package snoopy.didit.domain

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false, name = "created_date")
    var createdDate: LocalDateTime? = null
        protected set

    @UpdateTimestamp
    @Column(name = "updated_date")
    var updatedDate: LocalDateTime? = null
        protected set

    @Column
    var deleteFlag: Boolean = false
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
