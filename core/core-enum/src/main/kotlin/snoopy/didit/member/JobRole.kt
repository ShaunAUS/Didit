package snoopy.didit.member

enum class JobRole(
    val positionName: String,
    val jobFamily: JobFamily,
    val number: Int,
) {
    // 기획/비즈니스 직군
    NOT_SELECTED("선택안함", JobFamily.NOT_SELECTED, 0),
    SERVICE_PLANNER("서비스 기획자", JobFamily.PLANNING, 1),
    BUSINESS_PLANNER("사업 기획자", JobFamily.PLANNING, 2),
    PO_PM("PO/PM", JobFamily.PLANNING, 3),
    BUSINESS_DEVELOPER("사업개발", JobFamily.PLANNING, 4),
    SALES_MANAGER("세일즈 매니저", JobFamily.PLANNING, 5),
    STRATEGY_MANAGER("전략기획 매니저", JobFamily.PLANNING, 6),
    INVESTMENT_MANAGER("투자 담당자", JobFamily.PLANNING, 7),
    CEO("CEO", JobFamily.PLANNING, 8),
    CFO("CFO", JobFamily.PLANNING, 9),
    COO("COO", JobFamily.PLANNING, 10),
    CSO("CSO", JobFamily.PLANNING, 11),
    SERVICE_OPERATION_MANAGER("서비스 운영 매니저", JobFamily.PLANNING, 12),

    // 개발 직군
    BACKEND_DEVELOPER("백엔드 개발자", JobFamily.DEVELOPMENT, 13),
    FULLSTACK_DEVELOPER("풀스택 개발자", JobFamily.DEVELOPMENT, 14),
    WEB_PUBLISHER("웹 퍼블리셔", JobFamily.DEVELOPMENT, 15),
    DBA("DBA", JobFamily.DEVELOPMENT, 16),
    DEVELOPMENT_PM_PL("개발 PM/PL", JobFamily.DEVELOPMENT, 17),
    HARDWARE_ENGINEER("하드웨어 엔지니어", JobFamily.DEVELOPMENT, 18),
    EMBEDDED_SW_ENGINEER("임베디드 SW 엔지니어", JobFamily.DEVELOPMENT, 19),
    ROBOTICS_ENGINEER("로보틱스 엔지니어", JobFamily.DEVELOPMENT, 20),
    FRONTEND_DEVELOPER("웹 프론트엔드 개발자", JobFamily.DEVELOPMENT, 21),
    APP_DEVELOPER("앱 개발자", JobFamily.DEVELOPMENT, 22),
    DEVOPS_ENGINEER("Devops 엔지니어", JobFamily.DEVELOPMENT, 23),
    QA_ENGINEER("QA 엔지니어", JobFamily.DEVELOPMENT, 24),
    SECURITY_ENGINEER("보안 엔지니어", JobFamily.DEVELOPMENT, 25),
    FIRMWARE_ENGINEER("펌웨어 엔지니어", JobFamily.DEVELOPMENT, 26),
    EMBEDDED_HW_ENGINEER("임베디드 HW 엔지니어", JobFamily.DEVELOPMENT, 27),
    BLOCKCHAIN_DEVELOPER("블록체인 개발자", JobFamily.DEVELOPMENT, 28),

    // 디자인 직군
    PRODUCT_DESIGNER("프로덕트 디자이너", JobFamily.DESIGN, 29),
    WEB_DESIGNER("웹 디자이너", JobFamily.DESIGN, 30),
    CONTENT_DESIGNER("콘텐츠 디자이너", JobFamily.DESIGN, 31),
    UI_UX_DESIGNER("UI/UX 디자이너", JobFamily.DESIGN, 32),
    GRAPHIC_DESIGNER("그래픽 디자이너", JobFamily.DESIGN, 33),
    BRAND_DESIGNER("브랜드 디자이너", JobFamily.DESIGN, 34),

    // AI/데이터 직군
    DATA_ANALYST("데이터 분석가", JobFamily.AI_DATA, 35),
    DATA_ENGINEER("데이터 엔지니어", JobFamily.AI_DATA, 36),
    DATA_PLATFORM_ENGINEER("데이터 플랫폼 엔지니어", JobFamily.AI_DATA, 37),
    MACHINE_LEARNING_ENGINEER("머신러닝 엔지니어", JobFamily.AI_DATA, 38),
    DATAOPS_ENGINEER("Dataops 엔지니어", JobFamily.AI_DATA, 39),
    DATA_SCIENTIST("데이터 사이언티스트", JobFamily.AI_DATA, 40),
    DATA_ANALYTICS_ENGINEER("데이터 애널리틱스 엔지니어", JobFamily.AI_DATA, 41),
    AI_DEVELOPER("AI 개발자", JobFamily.AI_DATA, 42),
    MLOPS_ENGINEER("MLOps 엔지니어", JobFamily.AI_DATA, 43),

    // 마케팅 직군
    DIGITAL_MARKETER("디지털 마케터", JobFamily.MARKETING, 44),
    CONTENT_MARKETER("콘텐츠 마케터", JobFamily.MARKETING, 45),
    PERFORMANCE_MARKETER("퍼포먼스 마케터", JobFamily.MARKETING, 46),
    BRAND_MARKETER("브랜드 마케터", JobFamily.MARKETING, 47),
    AD_MARKETER("광고 마케터", JobFamily.MARKETING, 48),
    PR_MANAGER("PR 매니저", JobFamily.MARKETING, 49),
    CRM_MARKETER("CRM 마케터", JobFamily.MARKETING, 50),
    SNS_MARKETER("SNS 마케터", JobFamily.MARKETING, 51),

    // 운영 직군
    CS_MANAGER("CS 매니저", JobFamily.OPERATION, 52),
    CUSTOMER_SUPPORT_MANAGER("고객지원 매니저", JobFamily.OPERATION, 53),
    COMMUNITY_MANAGER("커뮤니티 매니저", JobFamily.OPERATION, 54),
    CONTENT_OPERATION_MANAGER("콘텐츠 운영 매니저", JobFamily.OPERATION, 55),
    QA_MANAGER("QA 매니저", JobFamily.OPERATION, 56),
    ;

    companion object {
        fun toJobRole(positionName: String): JobRole {
            return entries.first { it.positionName == positionName }
        }

        fun toInt(jobRole: JobRole): Int {
            return jobRole.number
        }
    }
}
