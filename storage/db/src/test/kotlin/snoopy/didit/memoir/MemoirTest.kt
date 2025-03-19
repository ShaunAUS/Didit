package snoopy.didit.memoir

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import snoopy.didit.domain.memoir.Memoir

class MemoirTest {
    // TODO 주입 문제 해결해서 Repository test 작성

    @Test
    fun `CreateMemoirDto에서_MemoirEntity로_변환한다`() {
        val createMemoirDto = MemoirTestFixture.getCreateMemoirDto()
        val result = Memoir.of(createMemoirDto)

        assertThat(result.title).isEqualTo("회고록 제목")
        assertThat(result.content).isEqualTo("회고록 내용")
        assertThat(result.template).isEqualTo(0)
        assertThat(result.spaceId).isEqualTo(1)
    }

    @Test
    fun `MemoirEntity에서_CreatedMemoirInfoDto로_변환한다`() {
        val memoirs = MemoirTestFixture.getMemoirWithId()
        val result = memoirs.toCreatedMemoirInfoDto()

        assertThat(result.title).isEqualTo("회고록 제목")
        assertThat(result.content).isEqualTo("회고록 내용")
        assertThat(result.writeDate).isEqualTo("2021-01-01T00:00:00")
    }

    @Test
    fun `MemoirEntity에서_MemoirInDto로_변환한다`() {
        val memoirs = MemoirTestFixture.getMemoirWithId()
        val result = memoirs.toMemoirInfoDto()

        assertThat(result.title).isEqualTo("회고록 제목")
        assertThat(result.content).isEqualTo("회고록 내용")
        assertThat(result.writeDate).isEqualTo("2021-01-01T00:00:00")
    }

    @Test
    fun `MemoirEntityf를_ModifyMemoirDto로_수정한다`() {
        val memoirs = MemoirTestFixture.getMemoir()
        val modifyMemoirDto = MemoirTestFixture.getModifyMemoirDto()
        memoirs.modify(modifyMemoirDto)

        assertThat(memoirs.title).isEqualTo("업데이트 회고록 제목")
        assertThat(memoirs.content).isEqualTo("업데이트 회고록 내용")
        assertThat(memoirs.template).isEqualTo(2)
    }

    @Test
    fun `MemoirEntity를_삭제하면_deleteFlag는_false가_된다`() {
        val memoirs = MemoirTestFixture.getMemoirsWithDeleteFlag()
        memoirs.delete()

        assertThat(memoirs.deleteFlag).isTrue
    }
}
