package snoopy.didit.domain.tag

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

@Component
class TagAppender(
    private val tagRepository: TagRepository,
) {
    @Transactional
    fun createTag(
        tagCreateDtos: List<TagCreateDto>,
        memoirId: Long,
    ): List<Tag> {
        return tagRepository.createTag(tagCreateDtos, memoirId)
    }

    @Transactional
    fun createTagByModifyDto(
        tags: List<TagUpdateDto>,
        memoirId: Long,
    ): List<Tag> {
        return tagRepository.createTagByModifyDto(tags, memoirId)
    }

    @Transactional
    fun delete(memoirId: Long): List<Tag> {
        return tagRepository.deleteBy(memoirId)
    }
}
