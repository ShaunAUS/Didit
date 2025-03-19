package snoopy.didit.tag

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import snoopy.didit.domain.tag.Tag
import snoopy.didit.domain.tag.dto.toResponse

private const val MEMOIR_ID = 1L

class TagTest {
    // TODO 주입 문제 해결해서 Repository test 작성

    @Test
    fun `TagEntity_를_CreateDto로_변환한다`() {
        val createTagDto = TagTestFixture.getCreateTagDto()
        val result = Tag.ofCreate(createTagDto, MEMOIR_ID)

        assertThat(result.name).isEqualTo("태그")
        assertThat(result.memoirId).isEqualTo(1)
    }

    @Test
    fun `TagEntity_를_TagInfoDto로_변환한다`() {
        val tagEntity = TagTestFixture.getTagEntity()
        val toResponse = tagEntity.toResponse()

        assertThat(toResponse.name).isEqualTo("태그")
    }

    @Test
    fun `TagEntity_를_ModifyTagDto로_변환한다`() {
        val modifyTagDto = TagTestFixture.getModifyTagDto()
        Tag.ofModify(modifyTagDto, MEMOIR_ID)

        assertThat(modifyTagDto.name).isEqualTo("업데이트 태그")
    }
}
