package snoopy.didit.domain.memoritag

import getOrThrow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import snoopy.didit.domain.memoir.Memoir
import snoopy.didit.domain.memoir.MemoirService
import snoopy.didit.domain.memoir.dto.MemoirCreateDto
import snoopy.didit.domain.memoir.dto.MemoirResponse
import snoopy.didit.domain.memoir.dto.MemoirTagWithDateResponse
import snoopy.didit.domain.memoir.dto.MemoirWithSpaceResponse
import snoopy.didit.domain.memoir.dto.ModifyMemoirDto
import snoopy.didit.domain.memoir.dto.toMemoirResponse
import snoopy.didit.domain.memoritag.dto.MemoirContinousWritingCalculateRequestDto
import snoopy.didit.domain.memoritag.dto.MemoirContinuousWritingResponse
import snoopy.didit.domain.memoritag.dto.MemoirTagResponse
import snoopy.didit.domain.memoritag.dto.WeekResponse
import snoopy.didit.domain.memoritag.dto.toMemoirTagResponse
import snoopy.didit.domain.randommessage.RandomMessageService
import snoopy.didit.domain.space.SpaceService
import snoopy.didit.domain.space.dto.SpaceResponse
import snoopy.didit.domain.tag.TagService
import snoopy.didit.domain.tag.dto.TagResponse
import snoopy.didit.domain.tag.dto.toResponse
import snoopy.didit.memoir.Calendar
import java.time.LocalDate
import java.time.LocalDateTime

private const val CURRENT_WEEK = 1
private const val FIRST_DAY_OF_MONTH = 1
private const val PLUS_ONE_MONTH = 1L
private const val MINUS_ONE_DAY = 1L
private const val PLUS_ONE_DAY = 1L
private const val SATURDAY = 6
private const val SUNDAY = 7
private const val INIT_CONTINOUS_WRITE_DAY = 1

@Service
class MemoirTagFacade(
    private val memoirService: MemoirService,
    private val tagService: TagService,
    private val spaceService: SpaceService,
    private val randomMessageService: RandomMessageService,
) {
    fun createMemoirTag(createMemoir: MemoirCreateDto): MemoirTagResponse {
        val createdMemoir = memoirService.createMemoir(createMemoir)
        return createdMemoir.toMemoirTagResponse(createdMemoir, createTags(createMemoir, createdMemoir))
    }

    fun findMemoirTagBy(memoirId: Long): MemoirTagResponse {
        val memoir = memoirService.findMemoirBy(memoirId)
        return memoir.toMemoirTagResponse(memoir, findTagResponseBy(memoirId))
    }

    fun findMemoirTagsByDate(
        memberId: Long,
        targetYear: Int,
        month: Calendar,
    ): List<MemoirTagWithDateResponse> {
        val targetMonth = Calendar.toInt(month)
        val memoirTagResponses = mutableListOf<MemoirTagResponse>()
        findMemoirTagsBy(findSpaces(memberId), memoirTagResponses)

        val result = mutableListOf<MemoirTagWithDateResponse>()
        val weekInfos = getMonthWeeks(targetYear, targetMonth)
        for (weekInfo in weekInfos) {
            result.add(filterMemoirTagResponseByWeeknInfo(targetMonth, weekInfo, memoirTagResponses))
        }
        return result
    }

    fun findMemoirTagsBy(memberId: Long): List<MemoirWithSpaceResponse> {
        val result = mutableListOf<MemoirWithSpaceResponse>()

        val spaces = findSpaces(memberId)
        for (space in spaces) {
            val memoirTagResponses = mutableListOf<MemoirTagResponse>()
            val memoirs = findUptoDateFourMemoirs(space)
            memoirs?.let { findMemoirTagsBy(memoirs, memoirTagResponses) }

            // 모든 space 결과에 추가 (회고록 있든 없든)
            result.add(MemoirWithSpaceResponse(space.name, memoirTagResponses))
        }
        return result
    }

    fun findMemoirTagsBy(
        spaceId: Long,
        pageable: Pageable,
    ): Page<MemoirTagResponse> {
        val memoirs = memoirService.findMemoirsBy(spaceId, pageable)
        val memoirTagResponse =
            memoirs?.let {
                memoirs.map { memoir -> memoir.toMemoirTagResponse(memoir, findTagResponseBy(memoir.id.getOrThrow())) }
            }
        return memoirTagResponse ?: Page.empty()
    }

    fun modifyMemoirTag(modifyMemoirDto: ModifyMemoirDto): MemoirTagResponse {
        val modifiedMemoir = memoirService.modifyMemoir(modifyMemoirDto)
        return modifiedMemoir.toMemoirTagResponse(modifiedMemoir, modifyTags(modifyMemoirDto))
    }

    fun deleteMemoirTagBy(memoirId: Long): MemoirResponse {
        val deleteMemoir = memoirService.deleteMemoirBy(memoirId)
        return deleteMemoir.toMemoirResponse(deleteMemoir)
    }

    fun calculateContinuousWritingMemoirTag(
        memberId: Long,
        memoirContinousWritingCalculateRequestDto: MemoirContinousWritingCalculateRequestDto,
    ): MemoirContinuousWritingResponse {
        val spaces = findSpaces(memberId)
        val year = memoirContinousWritingCalculateRequestDto.year
        val month = memoirContinousWritingCalculateRequestDto.month

        val writingDates = extractMemorisWritingDates(spaces)
        val filteredDates = filterWritingDatesBy(writingDates, year, month)

        var continuousWriteDay = INIT_CONTINOUS_WRITE_DAY
        var currentDate = getFirstDayOfMonth(year, month)
        val consecutiveDates = mutableListOf<LocalDate>()

        val today: LocalDateTime = LocalDateTime.now()
        while (checkByTodayDate(currentDate, today)) {
            if (currentDateIsWeekend(currentDate)) {
                currentDate = moveCurrnetDateToNextDay(currentDate)
                continue
            }

            if (currentDateIsToday(currentDate, today)) {
                continuousWriteDay =
                    didWriteTodayMemoir(filteredDates, today, continuousWriteDay, consecutiveDates, currentDate)
                break
            }

            if (writeMemoirInCurrentDate(filteredDates, currentDate)) {
                continuousWriteDay = plusContionuousWriteDay(continuousWriteDay, consecutiveDates, currentDate)
            }
            if (notWriteMemoirInCurrentDate(filteredDates, currentDate)) {
                continuousWriteDay = clearContinuousWirteDay(continuousWriteDay, consecutiveDates)
            }

            // 다음 날짜로 이동
            currentDate = moveCurrnetDateToNextDay(currentDate)
        }

        // 결과 반환
        return MemoirContinuousWritingResponse(
            continuousWriteDay = continuousWriteDay,
            writtenDates = consecutiveDates,
            totalMemoirCount = filteredDates.size,
            randomMessage = randomMessageService.createRandomMessage(),
        )
    }

    private fun clearContinuousWirteDay(
        continuousWriteDay: Int,
        consecutiveDates: MutableList<LocalDate>,
    ): Int {
        var continuousWriteDay1 = continuousWriteDay
        continuousWriteDay1 = 0
        consecutiveDates.clear()
        return continuousWriteDay1
    }

    private fun checkByTodayDate(
        currentDate: LocalDate,
        today: LocalDateTime,
    ) = currentDate <= today.toLocalDate()

    private fun didWriteTodayMemoir(
        filteredDates: List<LocalDate>,
        today: LocalDateTime,
        continuousWriteDay: Int,
        consecutiveDates: MutableList<LocalDate>,
        currentDate: LocalDate,
    ): Int {
        var continuousWriteDay1 = continuousWriteDay
        if (writeTodayMemoir(filteredDates, today)) {
            continuousWriteDay1++
            consecutiveDates.add(currentDate)
        }
        return continuousWriteDay1
    }

    private fun plusContionuousWriteDay(
        continuousWriteDay: Int,
        consecutiveDates: MutableList<LocalDate>,
        currentDate: LocalDate,
    ): Int {
        var continuousWriteDay1 = continuousWriteDay
        continuousWriteDay1++
        consecutiveDates.add(currentDate)
        return continuousWriteDay1
    }

    private fun writeTodayMemoir(
        filteredDates: List<LocalDate>,
        today: LocalDateTime,
    ) = writeMemoirInCurrentDate(filteredDates, today.toLocalDate())

    private fun writeMemoirInCurrentDate(
        filteredDates: List<LocalDate>,
        currentDate: LocalDate,
    ) = filteredDates.contains(currentDate)

    private fun notWriteMemoirInCurrentDate(
        filteredDates: List<LocalDate>,
        currentDate: LocalDate,
    ) = !filteredDates.contains(currentDate)

    private fun currentDateIsToday(
        currentDate: LocalDate,
        today: LocalDateTime,
    ) = currentDate == today.toLocalDate()

    private fun currentDateIsWeekend(currentDate: LocalDate) = currentDate.dayOfWeek.value in SATURDAY..SUNDAY

    private fun filterWritingDatesBy(
        writingDates: List<LocalDateTime>,
        year: Int,
        month: Int,
    ): List<LocalDate> {
        return writingDates
            .filter { it.year == year && it.monthValue == month }
            .map { it.toLocalDate() }
            .distinct()
            .sorted()
    }

    private fun extractMemorisWritingDates(spaces: List<SpaceResponse>): MutableList<LocalDateTime> {
        val writingDates = mutableListOf<LocalDateTime>()
        spaces.forEach { space ->
            val memoirs = findMemoirs(space)
            memoirs?.forEach { memoir ->
                writingDates.add(memoir.writeDate)
            }
        }
        return writingDates
    }

    private fun findMemoirs(space: SpaceResponse): List<Memoir>? {
        return memoirService.findMemoirsBy(space.id)
    }

    private fun findSpaces(memberId: Long): List<SpaceResponse> {
        return spaceService.findSpaceByMemberId(memberId)
    }

    private fun filterMemoirTagResponseByWeeknInfo(
        targetMonth: Int,
        weekResponse: WeekResponse,
        memoirTagResponses: List<MemoirTagResponse>,
    ): MemoirTagWithDateResponse {
        return MemoirTagWithDateResponse.of(
            targetMonth,
            weekResponse,
            filterByWeekInfo(memoirTagResponses, targetMonth, weekResponse),
        )
    }

    private fun filterByWeekInfo(
        memoirTagResponses: List<MemoirTagResponse>,
        targetMonth: Int,
        weekResponse: WeekResponse,
    ): List<MemoirTagResponse> {
        return memoirTagResponses.filter { memoirTagInfoDto ->
            getMonth(memoirTagInfoDto) == targetMonth && isDayOfMonthInWeekInfo(memoirTagInfoDto, weekResponse)
        }
    }

    private fun isDayOfMonthInWeekInfo(
        memoirTagInfoDto: MemoirTagResponse,
        weekResponse: WeekResponse,
    ) = getDayOfMonth(memoirTagInfoDto) in weekResponse.startDay..weekResponse.endDay

    private fun getDayOfMonth(memoirTagInfoDto: MemoirTagResponse) = memoirTagInfoDto.writeDate.dayOfMonth

    private fun getMonth(memoirTagInfoDto: MemoirTagResponse) = memoirTagInfoDto.writeDate.monthValue

    private fun findMemoirTagsBy(
        memoirs: List<Memoir>,
        memoirTagResponses: MutableList<MemoirTagResponse>,
    ) = memoirs.map { memoir ->
        memoirTagResponses.add(memoir.toMemoirTagResponse(memoir, findTagResponseBy(memoir.id.getOrThrow())))
    }

    private fun findUptoDateFourMemoirs(space: SpaceResponse): List<Memoir>? {
        return memoirService.findUptoDateMemoirsBy(space.id)
    }

    private fun findTagResponseBy(memoirId: Long) = tagService.findByMemoirId(memoirId)?.map { it.toResponse() }

    private fun modifyTags(modifyMemoirDto: ModifyMemoirDto): List<TagResponse> {
        val tags = (
            modifyMemoirDto.tags
                ?.let { tagService.modifyTags(modifyMemoirDto.tags, modifyMemoirDto.id.getOrThrow()) }
                ?: tagService.delete(modifyMemoirDto.id.getOrThrow())
        )
        return tags.map { it.toResponse() }
    }

    private fun getLastDayOfMonth(firstDayOfMonth: LocalDate): LocalDate {
        return firstDayOfMonth.plusMonths(PLUS_ONE_MONTH).minusDays(MINUS_ONE_DAY)
    }

    private fun getFirstDayOfMonth(
        year: Int,
        month: Int,
    ): LocalDate {
        return LocalDate.of(year, month, FIRST_DAY_OF_MONTH)
    }

    private fun createTags(
        createMemoir: MemoirCreateDto,
        memoir: Memoir,
    ): List<TagResponse>? {
        val createTags = createMemoir.tags?.let { tagService.createTag(it, memoir.id.getOrThrow()) }
        return createTags?.map { it.toResponse() }
    }

    private fun createWeekInfoList(
        weekResponseList: MutableList<WeekResponse>,
        year: Int,
        month: Int,
        startDate: LocalDate,
        actualEndDate: LocalDate,
    ) {
        weekResponseList.add(
            WeekResponse(
                year = year,
                month = month,
                weekNumber = CURRENT_WEEK,
                startDay = startDate.dayOfMonth,
                endDay = actualEndDate.dayOfMonth,
            ),
        )
    }

    private fun adjustEndDateWithinMonth(
        endDate: LocalDate,
        lastDayOfMonth: LocalDate,
    ): LocalDate {
        val actualEndDate =
            if (endDate.isAfter(lastDayOfMonth)) {
                lastDayOfMonth
            } else {
                endDate
            }
        return actualEndDate
    }

    private fun adjustDateToSunday(startDate: LocalDate): LocalDate {
        var startDate1 = startDate
        while (dateIsNotSundy(startDate1)) {
            startDate1 = moveCurrnetDateToNextDay(startDate1)
        }
        return startDate1
    }

    private fun dateIsNotSundy(startDate: LocalDate): Boolean {
        return startDate.dayOfWeek.value != SUNDAY
    }

    private fun getMonthWeeks(
        year: Int,
        month: Int,
    ): List<WeekResponse> {
        val firstDayOfMonth = getFirstDayOfMonth(year, month)
        val lastDayOfMonth = getLastDayOfMonth(firstDayOfMonth)

        val weekResponseList = mutableListOf<WeekResponse>()
        var startDate = firstDayOfMonth

        // 첫 주의 시작을 해당 월의 첫 번째 일요일까지로 조정
        startDate = adjustDateToSunday(startDate)
        var currentWeek = CURRENT_WEEK
        startDate = firstDayOfMonth

        while (startDate <= lastDayOfMonth) {
            // 해당 주의 마지막 날(일요일) 계산
            var endDate = startDate
            endDate = adjustDateToSunday(endDate)

            // 실제 마지막 날짜가 월의 마지막 날을 넘지 않도록 조정
            val actualEndDate = adjustEndDateWithinMonth(endDate, lastDayOfMonth)
            createWeekInfoList(weekResponseList, year, month, startDate, actualEndDate)

            // 다음 주 시작 날짜로 이동
            startDate = moveCurrnetDateToNextDay(actualEndDate)
            currentWeek++
        }

        return weekResponseList
    }

    private fun moveCurrnetDateToNextDay(currentDate: LocalDate): LocalDate {
        return currentDate.plusDays(PLUS_ONE_DAY)
    }

    private fun findMemoirTagsBy(
        spaces: List<SpaceResponse>,
        memoirTagResponses: MutableList<MemoirTagResponse>,
    ) {
        for (space in spaces) {
            val memoirs = findMemoirs(space)
            memoirs?.let { findMemoirTagsBy(memoirs, memoirTagResponses) }
        }
    }
}
