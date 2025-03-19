dependencies {

    implementation(project(":core:core-domain"))
    implementation(project(":core:core-enum"))

    implementation("org.springframework.boot:spring-boot-starter-mail")
    // 트랜잭션 사용을 위한 추가
    implementation("org.springframework:spring-tx:${property("springTxVersion")}")

    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")

    // 코루틴 사용을 위한 추가
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("coroutinesVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("coroutinesVersion")}")
}
