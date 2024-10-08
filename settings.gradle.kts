plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "zustand-wrapper"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("kotlinWrappers") {
            val wrapperVersion = "0.0.1-pre.814"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrapperVersion")
        }
        create("kotlinx") {
            library("coroutines-core", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
        }
    }
}