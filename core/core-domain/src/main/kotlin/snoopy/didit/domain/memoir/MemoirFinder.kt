package snoopy.didit.domain.memoir

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class MemoirFinder(
    private val memoirRepository: MemoirRepository,
) {
    fun findMemoirBy(memoirId: Long): Memoir {
        return memoirRepository.findMemoirBy(memoirId)
    }

    fun findUptoDateMemoirsBy(spaceId: Long): List<Memoir>? {
        return memoirRepository.findUptoDateMemoirsBy(spaceId)
    }

    fun findMemoirsBy(spaceId: Long): List<Memoir>? {
        return memoirRepository.findMemoirsBy(spaceId)
    }

    fun findMemoirsBy(
        spaceId: Long,
        pageable: Pageable,
    ): Page<Memoir>? {
        return memoirRepository.findMemoirsBy(spaceId, pageable)
    }

    fun findTodayMemoirsBy(memberId: Long): List<Memoir>? {
        return memoirRepository.findTodayMemoirsBy(memberId)
    }
}
