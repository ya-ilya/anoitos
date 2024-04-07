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
    implementation(project(":anoitos-lexer"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}