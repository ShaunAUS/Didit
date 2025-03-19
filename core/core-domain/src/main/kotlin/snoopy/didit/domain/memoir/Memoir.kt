package snoopy.didit.domain.memoir

import jakarta.persistence.Column
import jakarta.persistence.Entity
import snoopy.didit.domain.BaseEntity
import snoopy.didit.domain.memoir.dto.CreatedMemoirInfoDto
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.MemoirResponse
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto
import snoopy.didit.memoir.Template
import java.time.LocalDateTime

@Entity
class Memoir(
    title: String,
    content: String,
    writeDate: LocalDateTime,
    template: Int,
    spaceId: Long,
    memberId: Long,
) : BaseEntity() {
    @Column(nullable = false, length = 50)
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set

    @Column(nullable = false)
    var template: Int = template
        protected set

    @Column(nullable = false)
    var writeDate: LocalDateTime = writeDate
        protected set

    @Column(nullable = false)
    var memberId: Long = memberId
        protected set

    @Column(nullable = false)
    var spaceId: Long = spaceId
        protected set

    companion object {
        fun of(createMemoir: MemoirCreateDto): Memoir {
            return Memoir(
                title = createMemoir.title!!,
                content = createMemoir.content!!,
                writeDate = LocalDateTime.now(),
                spaceId = createMemoir.spaceId!!,
                memberId = 1,
                template = Template.toInt(createMemoir.template!!),
            )
        }
    }

    fun toCreatedMemoirInfoDto(): CreatedMemoirInfoDto {
        return CreatedMemoirInfoDto(
            id = id!!,
            writeDate = writeDate,
            title = title,
            content = content,
        )
    }

    fun toMemoirInfoDto(): MemoirResponse {
        return MemoirResponse(
            id = id!!,
            writeDate = writeDate,
            title = title,
            content = content,
        )
    }

    fun modify(modifyMemoirDto: ModifyMemoirDto) {
        title = modifyMemoirDto.title!!
        content = modifyMemoirDto.content!!
        template = Template.toInt(modifyMemoirDto.template!!)
    }

    fun delete() {
        deleteFlag = true
    }
}
