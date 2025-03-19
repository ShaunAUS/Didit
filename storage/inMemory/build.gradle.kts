dependencies {
    implementation(project(":global:error"))
    implementation(project(":global:support"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.bucket4j:bucket4j-redis:8.7.1")
}
