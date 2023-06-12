pluginManagement {
    repositories {
        google()
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
rootProject.name = "HoneyMart"
include(
    ":user",
    ":admin",
    ":owner",
    ":core",
    ":design_system"
)
include(":core:data")
include(":core:domain")
