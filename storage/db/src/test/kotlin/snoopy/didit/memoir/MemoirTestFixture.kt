package snoopy.didit.memoir

import snoopy.didit.domain.memoir.Memoir
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto
import java.time.LocalDateTime

internal class MemoirTestFixture {
    companion object {
        fun getCreateMemoirDto(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = "회고록 제목",
                content = "회고록 내용",
                template = Template.FREE,
                tags = emptyList(),
            )
        }

        fun getMemoir(): Memoir {
            return Memoir(
                title = "회고록 제목",
                content = "회고록 내용",
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                spaceId = 1,
                memberId = 1,
                template = 0,
            )
        }

        fun getMemoirWithId(testId: Long = 1L): Memoir {
            return Memoir(
                title = "회고록 제목",
                content = "회고록 내용",
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                spaceId = 1,
                memberId = 1,
                template = 0,
            )
                .also { memoirs ->
                    memoirs.javaClass.superclass?.getDeclaredField("id")?.apply {
                        isAccessible = true
                        set(memoirs, testId)
                    }
                }
        }

        fun getMemoirsWithDeleteFlag(deleteFlag: Boolean = false): Memoir {
            return Memoir(
                title = "회고록 제목",
                content = "회고록 내용",
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                spaceId = 1,
                memberId = 1,
                template = 0,
            )
                .also { memoirs ->
                    memoirs.javaClass.superclass?.getDeclaredField("deleteFlag")?.apply {
                        isAccessible = true
                        set(memoirs, deleteFlag)
                    }
                }
        }

        fun getModifyMemoirDto(): ModifyMemoirDto {
            return ModifyMemoirDto(
                id = 1,
                title = "업데이트 회고록 제목",
                content = "업데이트 회고록 내용",
                template = Template.KPT,
                tags = emptyList(),
            )
        }
    }
}
