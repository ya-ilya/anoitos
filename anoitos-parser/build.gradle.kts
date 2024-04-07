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
    implementation(project(":anoitos-interpreter"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}