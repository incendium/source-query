plugins {
    `common-configuration`
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
        val jvmMain by getting {
            dependencies {
                implementation(project(":core"))

                implementation(libs.clikt)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(libs.bundles.commonTest.jvm)
            }
        }
    }
}
