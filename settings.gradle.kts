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
    }
}

include(
    ":app",
    ":api",
    ":search",
    ":pokemon:presentation",
    ":pokemon:domain",
    ":pokemon:data"
)
