plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "com.krushiler"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testApi(kotlin("test"))
    testApi("org.junit.jupiter:junit-jupiter-params:5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}