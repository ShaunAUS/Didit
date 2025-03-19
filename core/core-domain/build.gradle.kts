allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation(project(":core:core-enum"))
    implementation(project(":global:error"))
    implementation(project(":global:support"))
    implementation(project(":storage:inMemory"))

    // 파라미터 없는 기본 생성자를 사용하기 위해 no-arg 라이브러리 추가
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // 트랜잭션 사용을 위한 추가
    implementation("org.springframework:spring-tx:${property("springTxVersion")}")

    // 코루틴 사용을 위한 추가
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("coroutinesVersion")}")

    implementation("org.springframework.boot:spring-boot-starter-web")
    // logger
    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("swaggerVersion")}")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jakarta")

    compileOnly("org.springframework:spring-context")

    // Mockk
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")
    testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")

    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.security:spring-security-crypto")
}
