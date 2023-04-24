plugins {
    `common-configuration`
    `kotlin-library`
    detekt
    versions
}

group = "com.iamincendium"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()

        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.michaelBull.kotlin.result)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.commonTest.common)
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(libs.bundles.commonTest.jvm)
            }
        }
    }
}
