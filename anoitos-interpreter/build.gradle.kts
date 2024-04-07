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
    implementation(project(":anoitos-parser"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}