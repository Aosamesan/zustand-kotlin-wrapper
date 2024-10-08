import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi


plugins {
    kotlin("multiplatform") version "2.0.20"
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "/static/js/bundle.js"
                devServer?.open = false
            }
        }
        binaries.executable()
    }
    sourceSets {
        jsMain {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation(kotlinWrappers.react)
                implementation(kotlinWrappers.reactDom)
                implementation(npm("zustand", "4.5.5"))
            }
        }
        jsTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}