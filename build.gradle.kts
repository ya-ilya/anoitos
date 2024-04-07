plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.anoitos"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}