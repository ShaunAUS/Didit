package snoopy.didit.memoir

enum class Calendar(
    val monthName: String,
    val number: Int,
) {
    JANUARY("1월", 1),
    FEBRUARY("2월", 2),
    MARCH("3월", 3),
    APRIL("4월", 4),
    MAY("5월", 5),
    JUNE("6월", 6),
    JULY("7월", 7),
    AUGUST("8월", 8),
    SEPTEMBER("9월", 9),
    OCTOBER("10월", 10),
    NOVEMBER("11월", 11),
    DECEMBER("12월", 12),
    ;

    companion object {
        fun toMonth(number: Int): Calendar {
            return entries.first { it.number == number }
        }

        fun toInt(month: Calendar): Int {
            return month.number
        }
    }
}
