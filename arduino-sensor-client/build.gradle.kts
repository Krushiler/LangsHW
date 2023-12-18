plugins {
    kotlin("jvm")
    application
}

group = "com.krushiler"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val exposedVersion = "0.45.0"

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

    implementation("org.xerial:sqlite-jdbc:3.44.1.0")

    implementation("org.zeromq:jeromq:0.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}

application {
    mainClass.set("MainKt")
}