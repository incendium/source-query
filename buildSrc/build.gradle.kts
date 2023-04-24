plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(libs.gradle.plugin.kotlin)
    implementation(libs.gradle.plugin.kotlinxSerialization)
    implementation(libs.gradle.plugin.detekt)
    implementation(libs.gradle.plugin.versions)
    implementation(libs.gradle.plugin.versionsFilter)
}
