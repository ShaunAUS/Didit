package snoopy.didit.domain.memoir

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import snoopy.didit.domain.memoir.dto.MemoirCreateDto

interface MemoirRepository {
    fun findUptoDateMemoirsBy(spaceId: Long): List<Memoir>?

    fun findMemoirBy(memoirId: Long): Memoir

    fun createMemoir(createMemoir: MemoirCreateDto): Memoir

    fun findMemoirsBy(spaceId: Long): List<Memoir>?

    fun findMemoirsBy(
        spaceId: Long,
        pageable: Pageable,
    ): Page<Memoir>?

    fun saveMemoir(memoir: Memoir): Memoir

    fun findTodayMemoirsBy(memberId: Long): List<Memoir>?
}
