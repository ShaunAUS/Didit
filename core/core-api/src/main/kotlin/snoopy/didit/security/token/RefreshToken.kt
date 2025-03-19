package snoopy.didit.security.token

class RefreshToken private constructor(
    value: String,
) : Token(value) {
    companion object {
        fun of(value: String): RefreshToken = RefreshToken(value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RefreshToken) return false
        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
