import org.gradle.api.JavaVersion

plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    mavenLocal()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xemit-jvm-type-annotations" + "-Xjsr305=strict"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
