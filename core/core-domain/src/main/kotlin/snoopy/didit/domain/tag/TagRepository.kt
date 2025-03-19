package snoopy.didit.domain.tag

import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

interface TagRepository {
    fun createTag(
        tagCreateDtos: List<TagCreateDto>,
        memoirId: Long,
    ): List<Tag>

    fun findByMemoirId(memoirId: Long): List<Tag>?

    fun deleteBy(memoirId: Long): List<Tag>

    fun createTagByModifyDto(
        tags: List<TagUpdateDto>,
        memoirId: Long,
    ): List<Tag>
}
