tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(project(":core:core-enum"))
    implementation(project(":core:core-domain"))
    implementation(project(":global:logging"))
    implementation(project(":storage:inMemory"))
    implementation(project(":global:error"))
    implementation(project(":global:support"))
    implementation(project(":global:monitoring"))

    implementation("com.googlecode.json-simple:json-simple:1.1.1")

    runtimeOnly(project(":storage:db"))
    runtimeOnly(project(":batch:smtp"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("swaggerVersion")}")

    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingVersion")}")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${property("jjwtVersion")}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${property("jjwtVersion")}")

    // Mocking library
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")
    testImplementation("com.ninja-squad:springmockk:${property("mockkBeanVersion")}")

    testImplementation("org.springframework.security:spring-security-test")
}
