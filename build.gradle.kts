import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "se.horv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task<JavaExec>("generateDay") {
    group = "Custom"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("generator/GeneratorKt")
}


application {
    mainClass.set("solutions/MainKt")
}