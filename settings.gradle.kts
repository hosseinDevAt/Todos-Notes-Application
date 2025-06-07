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
        plugins{
            id("org.jetbrains.kotlin.android") version "2.1.10"
            id("org.jetbrains.kotlin.jvm") version "2.1.10"
            id("org.jetbrains.kotlin.kapt") version "2.1.10"
            id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Notes App"
include(":app")
 