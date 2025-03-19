package snoopy.didit.tag

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import snoopy.didit.domain.tag.QTag.tag
import snoopy.didit.domain.tag.Tag
import snoopy.didit.domain.tag.TagRepository
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagUpdateDto

@Repository
class TagRepositoryImpl(
    private val tagJpaRepository: TagJpaRepository,
    private val query: JPAQueryFactory,
) : TagRepository {
    override fun createTag(
        tagCreateDtos: List<TagCreateDto>,
        memoirId: Long,
    ): List<Tag> {
        return tagJpaRepository.saveAll(tagCreateDtos.map { createTagDto -> Tag.ofCreate(createTagDto, memoirId) })
    }

    override fun findByMemoirId(memoirId: Long): List<Tag>? {
        return query.selectFrom(tag)
            .where(tag.memoirId.eq(memoirId))
            .fetch()
            .takeIf { it.isNotEmpty() }
    }

    override fun deleteBy(memoirId: Long): List<Tag> {
        query.delete(tag)
            .where(tag.memoirId.eq(memoirId))
            .execute()
        return emptyList<Tag>()
    }

    override fun createTagByModifyDto(
        tags: List<TagUpdateDto>,
        memoirId: Long,
    ): List<Tag> {
        return tagJpaRepository.saveAll(tags.map { modifyTagDto -> Tag.ofModify(modifyTagDto, memoirId) })
    }
}
