import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.robowizard"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(compose.desktop.currentOs)
                implementation(files("plugin/KRPlugin.jar"))

                implementation(project(":KRobot"))
                implementation(project(":KRobot:KAS"))
                implementation(project(":KRobot:tcpClient"))

                // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-swing
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KRPlatform"
            packageVersion = "1.0.0"
        }
    }
}
