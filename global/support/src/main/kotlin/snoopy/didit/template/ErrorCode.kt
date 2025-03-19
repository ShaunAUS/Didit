package snoopy.didit.template

enum class ErrorCode(
    val httpStatus: Int,
    val errorCode: String,
    val message: String,
) {
    // Validation
    INVALID_INPUT_VALUE(600, "V001", "Invalid Input Value"),
    INVALID_VERIFICATION_CODE(600, "V002", "Invalid Verification Code"),

    // Memoir
    NOT_FOUD_MEMOIR(600, "M001", "Not Found Memoir"),

    // Space
    NOT_FOUND_SPACE(600, "S001", "Not Found Space"),

    // Alarm
    NOT_FOUND_ALARM(600, "A001", "Not Found Alarm"),

    // DTO
    DTO_NULL_FIELD_EXCEPTION(600, "D001", "Dto Null Field Exception"),
    PK_ERROR(600, "D002", "PK NOT Mapping Error"),

    // SSE
    SSE_CONNECTION_ERROR(600, "SSE001", "SSE Connection Error"),

    // Notification
    NOT_FOUND_NOTIFICATION(600, "N001", "Not Found Notification"),

    // Auth
    NOT_FOUND_TOKEN(600, "TK001", "Not Found Token"),
    EXPIRED_TOKEN(600, "TK002", "Expired Token"),
    ACCESS_DENIED(600, "TK003", "Access Denied"),
    INVALID_START_TOKEN(600, "TK004", "Invalid Start Token"),
    INVALID_SIGNATURE(600, "TK005", "Invalid Signature"),
    INVALID_TOKEN(600, "TK006", "Invalid Token"),
    LOGOUT_TOKEN(600, "TK007", "Logout Token"),
    FAIL_EXTRACTION_TOKEN(600, "TK008", "Fail Extraction Token"),
    INVALID_LOGIN_INFO(600, "TK009", "Invalid Login Info"),
    INVALID_REFRESH_TOKEN(600, "TK010", "Invalid Refresh Token"),
    INVALID_PERMISSION(600, "TK011", "Invalid Permission"),

    // Member
    EMAIL_ALREADY_EXISTS(600, "MB001", "Email Already Exists"),
    NOT_FOUND_MEMBER(600, "MB002", "Not Found Member"),

    // Resume
    NOT_FOUND_RESUME(600, "R001", "Not Found Resume"),
    NOT_FOUND_CAREER(600, "R002", "Not Found Career"),

    // RateLimt
    RATE_LIMIT(600, "RL001", "Rate Limit Exceeded"),
}
