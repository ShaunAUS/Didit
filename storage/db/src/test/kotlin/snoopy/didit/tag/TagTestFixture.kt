package snoopy.didit.tag

import snoopy.didit.domain.tag.Tag
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

internal class TagTestFixture {
    companion object {
        fun getTagEntity(): Tag {
            return Tag(
                name = "태그",
                memoirId = 1,
            )
        }

        fun getCreateTagDto(): TagCreateDto {
            return TagCreateDto(
                name = "태그",
            )
        }

        fun getModifyTagDto(): TagUpdateDto {
            return TagUpdateDto(
                name = "업데이트 태그",
            )
        }
    }
}
