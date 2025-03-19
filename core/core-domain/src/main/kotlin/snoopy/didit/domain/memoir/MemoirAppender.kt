package snoopy.didit.domain.memoir

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import snoopy.didit.domain.memoir.dto.MemoirCreateDto

@Component
class MemoirAppender(
    private val memoirRepository: MemoirRepository,
) {
    @Transactional
    fun createMemoir(createMemoir: MemoirCreateDto): Memoir {
        return memoirRepository.createMemoir(createMemoir)
    }

    @Transactional
    fun saveMemoir(memoir: Memoir): Memoir {
        return memoirRepository.saveMemoir(memoir)
    }
}
