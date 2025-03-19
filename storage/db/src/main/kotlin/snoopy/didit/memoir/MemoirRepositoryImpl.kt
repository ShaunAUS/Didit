package snoopy.didit.memoir

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import snoopy.didit.domain.memoir.Memoir
import snoopy.didit.domain.memoir.MemoirRepository
import snoopy.didit.domain.memoir.QMemoir.memoir
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.exception.BusinessException
import snoopy.didit.template.ErrorCode
import java.time.LocalDate

private const val LIMIT_MEMOIR_COUNT = 4L

private const val EMPTY_PAGE = 0L

@Repository
class MemoirRepositoryImpl(
    private val memoirJpaRepository: MemoirJpaRepository,
    private val query: JPAQueryFactory,
) : MemoirRepository {
    override fun findUptoDateMemoirsBy(spaceId: Long): List<Memoir>? {
        return query.selectFrom(memoir)
            .where(memoir.spaceId.eq(spaceId).and(memoir.deleteFlag.eq(false)))
            .orderBy(memoir.writeDate.desc())
            .limit(LIMIT_MEMOIR_COUNT)
            .fetch()
    }

    override fun findMemoirsBy(spaceId: Long): List<Memoir>? {
        return query.selectFrom(memoir)
            .where(memoir.spaceId.eq(spaceId).and(memoir.deleteFlag.eq(false)))
            .fetch()
    }

    override fun findMemoirsBy(
        spaceId: Long,
        pageable: Pageable,
    ): Page<Memoir>? {
        val memoir =
            query.selectFrom(memoir)
                .where(memoir.spaceId.eq(spaceId).and(memoir.deleteFlag.eq(false)))
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        return PageableExecutionUtils.getPage(memoir, pageable) { countQuery() }
    }

    override fun saveMemoir(memoir: Memoir): Memoir {
        return memoirJpaRepository.save(memoir)
    }

    override fun findTodayMemoirsBy(memberId: Long): List<Memoir>? {
        val today = LocalDate.now()
        val todayMonth = today.monthValue
        val todayDay = today.dayOfMonth

        return query.selectFrom(memoir)
            .where(
                memoir.memberId.eq(memberId)
                    .and(memoir.deleteFlag.eq(false))
                    .and(memoir.writeDate.month().eq(todayMonth))
                    .and(memoir.writeDate.dayOfMonth().eq(todayDay)),
            )
            .fetch()
    }

    private fun countQuery(): Long {
        return query
            .select(memoir.count())
            .from(memoir)
            .where(memoir.deleteFlag.eq(false))
            .fetchOne() ?: EMPTY_PAGE
    }

    override fun findMemoirBy(memoirId: Long): Memoir {
        return query.selectFrom(memoir)
            .where(memoir.id.eq(memoirId).and(memoir.deleteFlag.eq(false)))
            .fetchOne() ?: throw BusinessException(ErrorCode.NOT_FOUD_MEMOIR)
    }

    override fun createMemoir(createMemoir: MemoirCreateDto): Memoir {
        return memoirJpaRepository.save(Memoir.of(createMemoir))
    }
}
