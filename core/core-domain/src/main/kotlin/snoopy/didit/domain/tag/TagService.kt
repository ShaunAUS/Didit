package snoopy.didit.domain.tag

import org.springframework.stereotype.Service
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

@Service
class TagService(
    private val tagAppender: TagAppender,
    private val tagFinder: TagFinder,
) {
    fun createTag(
        tags: List<TagCreateDto>,
        memoirId: Long,
    ): List<Tag> {
        return tagAppender.createTag(tags, memoirId)
    }

    fun findByMemoirId(memoirId: Long): List<Tag>? {
        return tagFinder.findByMemoirId(memoirId)
    }

    fun modifyTags(
        tags: List<TagUpdateDto>,
        memoirId: Long,
    ): List<Tag> {
        tagAppender.delete(memoirId)
        return tagAppender.createTagByModifyDto(tags, memoirId)
    }

    fun delete(memoirId: Long): List<Tag> {
        return tagAppender.delete(memoirId)
    }
}
