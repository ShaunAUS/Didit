package snoopy.didit.security.constants

object PermitPathConstants {
    object Auth {
        const val LOGIN = "/api/v1/auth/login"
        const val REISSUE = "/api/v1/auth/reissue"
    }

    object Google {
        const val SOCIAL_LOGIN = "/api/v1/auth/test/**"
        const val SOCIAL_LOGIN_CALLBACK = "/api/v1/auth/test/**"
    }

    object Swagger {
        const val SWAGGER = "/v3/api-docs/**"
        const val SWAGGER_UI = "/swagger-ui/**"
    }

    object Monitoring {
        const val PROMETHEUS = "/actuator/prometheus"
    }

    object Signup {
        const val SIGNUP = "/api/v1/signup"
    }
}
