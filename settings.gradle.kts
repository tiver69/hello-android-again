dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.google.dagger.hilt.android") version "2.52"
        id("org.jetbrains.kotlin.jvm") version "2.0.20"
    }
}

include(
    ":app",
    ":common:navigation",
    ":common:api",
    ":search",
    ":pokemon:presentation",
    ":pokemon:domain",
    ":pokemon:data"
)
