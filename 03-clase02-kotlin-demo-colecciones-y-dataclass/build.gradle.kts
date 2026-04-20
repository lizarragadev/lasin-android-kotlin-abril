plugins {
    kotlin("jvm") version "2.1.10"
    application
}

group = "bo.edu.umsa.curso"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("bo.edu.umsa.curso.clase02.colecciones.ColeccionesDemoKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
