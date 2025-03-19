package snoopy.didit.memoir

enum class Weekday(
    val weekDayName: String,
    val number: Int,
) {
    MONDAY("월요일", 0),
    TUESDAY("화요일", 1),
    WEDNESDAY("수요일", 2),
    THURSDAY("목요일", 3),
    FRIDAY("금요일", 4),
    EVERYDAY("매일", 5),
    ;

    companion object {
        fun toWeekday(number: Int): Weekday {
            return entries.first { it.number == number }
        }

        fun toInt(weekday: Weekday): Int {
            return weekday.number
        }
    }

    fun isSameDayOfweek(nowDayOfWeek: String): Boolean {
        return weekDayName == nowDayOfWeek
    }
}
