repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}
dependencies {
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")
    implementation("com.github.napstr:logback-discord-appender:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}
