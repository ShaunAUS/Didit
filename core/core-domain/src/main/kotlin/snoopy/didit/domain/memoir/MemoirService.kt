package snoopy.didit.domain.memoir

import getOrThrow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto

@Service
class MemoirService(
    private val memoirFinder: MemoirFinder,
    private val memoirAppender: MemoirAppender,
) {
    fun createMemoir(createMemoir: MemoirCreateDto): Memoir {
        return memoirAppender.createMemoir(createMemoir)
    }

    fun modifyMemoir(modifyMemoirDto: ModifyMemoirDto): Memoir {
        val memoir = memoirFinder.findMemoirBy(modifyMemoirDto.id.getOrThrow())
        memoir.modify(modifyMemoirDto)
        return memoirAppender.saveMemoir(memoir)
    }

    fun deleteMemoirBy(memoirId: Long): Memoir {
        val memoir = memoirFinder.findMemoirBy(memoirId)
        memoir.delete()
        return memoirAppender.saveMemoir(memoir)
    }

    fun findMemoirBy(memoirId: Long): Memoir {
        return memoirFinder.findMemoirBy(memoirId)
    }

    fun findUptoDateMemoirsBy(spaceId: Long): List<Memoir>? {
        return memoirFinder.findUptoDateMemoirsBy(spaceId)
    }

    fun findMemoirsBy(spaceId: Long): List<Memoir>? {
        return memoirFinder.findMemoirsBy(spaceId)
    }

    fun findTodayMemoirsBy(memberId: Long): List<Memoir>? {
        return memoirFinder.findTodayMemoirsBy(memberId)
    }

    fun findMemoirsBy(
        spaceId: Long,
        pageable: Pageable,
    ): Page<Memoir>? {
        return memoirFinder.findMemoirsBy(spaceId, pageable)
    }
}
