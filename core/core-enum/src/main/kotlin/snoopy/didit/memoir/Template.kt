package snoopy.didit.memoir

enum class Template(
    val templateName: String,
    val number: Int,
) {
    FREE("자유로운 양식", 0),
    FIVEWHYS("5Whys", 1),
    KPT("KPT", 2),
    FOURLS("4Ls", 3),
    ;

    companion object {
        fun toTemplate(number: Int): Template {
            return entries.first { it.number == number }
        }

        fun toInt(template: Template): Int {
            return template.number
        }
    }
}
