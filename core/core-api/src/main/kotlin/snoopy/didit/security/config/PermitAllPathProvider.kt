package snoopy.didit.security.config

import org.springframework.stereotype.Component
import snoopy.didit.security.constants.PermitPathConstants

@Component
class PermitAllPathProvider {
    val publicPaths =
        arrayOf(
            PermitPathConstants.Signup.SIGNUP,
            PermitPathConstants.Auth.LOGIN,
            PermitPathConstants.Auth.REISSUE,
            PermitPathConstants.Google.SOCIAL_LOGIN,
            PermitPathConstants.Google.SOCIAL_LOGIN_CALLBACK,
            PermitPathConstants.Swagger.SWAGGER,
            PermitPathConstants.Swagger.SWAGGER_UI,
            PermitPathConstants.Monitoring.PROMETHEUS,
        )
}
