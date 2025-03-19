dependencies {
    implementation(project(":global:support"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")
}
