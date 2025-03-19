package snoopy.didit.member

enum class JobFamily(
    val familyName: String,
    val number: Int,
) {
    NOT_SELECTED("선택안함", 0),
    PLANNING("기획/비즈니스", 1),
    DEVELOPMENT("개발", 2),
    DESIGN("디자인", 3),
    AI_DATA("AI/데이터", 4),
    MARKETING("마케팅", 5),
    OPERATION("운영", 6),
    ;

    companion object {
        fun toJobFamily(familyName: String): JobFamily {
            return entries.first { it.familyName == familyName }
        }

        fun toInt(jobFamily: JobFamily): Int {
            return jobFamily.number
        }
    }
}
