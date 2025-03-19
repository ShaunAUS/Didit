package snoopy.didit.domain.space

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.space.dto.SpaceCreateDto

@Entity
class Space(name: String, memberId: Long) : BaseEntity() {
    @Column(nullable = false, length = 50)
    var name: String = name
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    companion object {
        fun of(spaceCreateDto: SpaceCreateDto): Space {
            return Space(
                name = spaceCreateDto.name!!,
                memberId = 1,
            )
        }
    }

    fun modifySpaceName(name: String) {
        this.name = name
    }

    fun deleteSpace() {
        this.deleteFlag = true
    }

    fun delete() {
        this.deleteFlag = true
    }
}
