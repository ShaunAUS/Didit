package snoopy.didit.domain.tag

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

@Entity
class Tag(
    name: String,
    memoirId: Long,
) {
    @Column(nullable = false, length = 20)
    var name: String = name
        protected set

    @Column(nullable = false)
    var memoirId: Long = memoirId
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    companion object {
        fun ofCreate(
            tagCreateDto: TagCreateDto,
            memoirId: Long,
        ): Tag {
            return Tag(
                name = tagCreateDto.name!!,
                memoirId = memoirId,
            )
        }

        fun ofModify(
            tagUpdateDto: TagUpdateDto,
            memoirId: Long,
        ): Tag {
            return Tag(
                name = tagUpdateDto.name!!,
                memoirId = memoirId,
            )
        }
    }
}
