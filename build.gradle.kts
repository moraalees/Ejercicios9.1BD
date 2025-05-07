plugins {
    kotlin("jvm") version "2.0.20"
}

group = "es.prog2425.ejerciciosBD9_1"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.0.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}