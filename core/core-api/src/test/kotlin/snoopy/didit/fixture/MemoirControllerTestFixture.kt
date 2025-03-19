package snoopy.didit.fixture

import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.MemoirResponse
import snoopy.didit.domain.memoir.dto.MemoirTagWithDateResponse
import snoopy.didit.domain.memoir.dto.MemoirWithSpaceResponse
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto
import snoopy.didit.domain.memoritag.dto.MemoirContinuousWritingResponse
import snoopy.didit.domain.memoritag.dto.MemoirTagResponse
import snoopy.didit.domain.tag.dto.TagCreateDto
import snoopy.didit.domain.tag.dto.TagResponse
import snoopy.didit.domain.tag.dto.TagUpdateDto
import snoopy.didit.memoir.Template
import java.time.LocalDate
import java.time.LocalDateTime

internal class MemoirControllerTestFixture {
    companion object {
        fun getCreateMemoirRequest(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = "회고록 제목",
                content = "회고록 내용",
                template = Template.FREE,
                tags =
                    listOf(
                        TagCreateDto(name = "태그1"),
                        TagCreateDto(name = "태그2"),
                    ),
            )
        }

        fun getMemoirTagInfoDto(): MemoirTagResponse {
            return MemoirTagResponse(
                id = 1,
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                title = "회고록 제목",
                content = "회고록 내용",
                tags =
                    listOf(
                        TagResponse(name = "태그1"),
                        TagResponse(name = "태그2"),
                    ),
            )
        }

        fun getModifyMemoirRequest(): ModifyMemoirDto {
            return ModifyMemoirDto(
                id = 1,
                title = "업데이트 회고록 제목",
                content = "업데이트 회고록 내용",
                template = Template.FREE,
                tags =
                    listOf(
                        TagUpdateDto(name = "업데이트 태그1"),
                        TagUpdateDto(name = "업데이트 태그2"),
                    ),
            )
        }

        fun getModifiedMemoirTagInfoDto(): MemoirTagResponse {
            return MemoirTagResponse(
                id = 1,
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                title = "업데이트 회고록 제목",
                content = "업데이트 회고록 내용",
                tags =
                    listOf(
                        TagResponse(name = "업데이트 태그1"),
                        TagResponse(name = "업데이트 태그2"),
                    ),
            )
        }

        fun getMemoirInfoDto(): MemoirResponse {
            return MemoirResponse(
                id = 1,
                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                title = "회고록 제목2",
                content = "회고록 내용2",
            )
        }

        fun getNullTitleCreateMemoirRequest(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = null,
                content = "회고록 내용",
                template = Template.FREE,
                tags =
                    listOf(
                        TagCreateDto(name = "태그1"),
                        TagCreateDto(name = "태그2"),
                    ),
            )
        }

        fun getTitleIsMoreThan50CreateMemoirRequest(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = "...................................................................................",
                content = "회고록 내용",
                template = Template.FREE,
                tags =
                    listOf(
                        TagCreateDto(name = "태그1"),
                        TagCreateDto(name = "태그2"),
                    ),
            )
        }

        fun getCreateMemoirRequestWithOutTemplate(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = "회고록 제목",
                content = "회고록 내용",
                template = null,
                tags =
                    listOf(
                        TagCreateDto(name = "태그1"),
                        TagCreateDto(name = "태그2"),
                    ),
            )
        }

        fun getCreateMemoirRequestWithNullTitleTags(): MemoirCreateDto {
            return MemoirCreateDto(
                spaceId = 1,
                title = "회고록 제목",
                content = "회고록 내용",
                template = Template.FREE,
                tags =
                    listOf(
                        TagCreateDto(name = null),
                    ),
            )
        }

        fun getMemoirInfoDtos(): List<MemoirWithSpaceResponse> {
            return listOf(
                MemoirWithSpaceResponse(
                    spaceName = "공간1",
                    memoirTagResponses =
                        mutableListOf(
                            MemoirTagResponse(
                                id = 1,
                                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                                title = "회고록 제목",
                                content = "회고록 내용",
                                tags =
                                    listOf(
                                        TagResponse(name = "태그1"),
                                        TagResponse(name = "태그2"),
                                    ),
                            ),
                        ),
                ),
                MemoirWithSpaceResponse(
                    spaceName = "공간2",
                    memoirTagResponses =
                        mutableListOf(
                            MemoirTagResponse(
                                id = 2,
                                writeDate = LocalDateTime.of(2021, 1, 1, 0, 0),
                                title = "회고록 제목2",
                                content = "회고록 내용2",
                                tags =
                                    listOf(
                                        TagResponse(name = "태그3"),
                                        TagResponse(name = "태그4"),
                                    ),
                            ),
                        ),
                ),
            )
        }

        fun getMemoirInfoWithDateDtos(): MemoirTagWithDateResponse {
            return MemoirTagWithDateResponse(
                month = 1,
                weekNumber = 1,
                startDay = 5,
                endDay = 15,
                memoirTagResponses =
                    mutableListOf(
                        MemoirTagResponse(
                            id = 1,
                            writeDate = LocalDateTime.of(2021, 1, 7, 0, 0),
                            title = "회고록 제목",
                            content = "회고록 내용",
                            tags =
                                listOf(
                                    TagResponse(name = "태그1"),
                                    TagResponse(name = "태그2"),
                                ),
                        ),
                    ),
            )
        }

        fun getMemoirContinuousWritingDto(): MemoirContinuousWritingResponse {
            return MemoirContinuousWritingResponse(
                continuousWriteDay = 3,
                writtenDates =
                    mutableListOf(
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 1, 2),
                        LocalDate.of(2021, 1, 3),
                    ),
                totalMemoirCount = 3,
                randomMessage = "랜덤 메시지",
            )
        }
    }
}
