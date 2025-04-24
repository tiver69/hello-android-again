pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hello Android Again"
include(":app")
include(":core")
include(":auth")
include(":auth:auth-presentation")
include(":tournament")
include(":tournament:tournament_data")
include(":tournament:tournament_domain")
include(":tournament:tournament_presentation")
