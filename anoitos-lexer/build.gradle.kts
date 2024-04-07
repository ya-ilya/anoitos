val anoitosVersion: String by project

plugins {
    kotlin("jvm")
}

group = "org.anoitos"
version = anoitosVersion

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}