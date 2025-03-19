package snoopy.didit.domain.tag

import org.springframework.stereotype.Component

@Component
class TagFinder(
    private val tagRepository: TagRepository,
) {
    fun findByMemoirId(memoirId: Long): List<Tag>? {
        return tagRepository.findByMemoirId(memoirId)
    }
}
