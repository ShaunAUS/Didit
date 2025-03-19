package snoopy.didit.security.token

class AccessToken private constructor(
    value: String,
) : Token(value) {
    companion object {
        fun of(value: String): AccessToken = AccessToken(value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccessToken) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int = value.hashCode()
}
