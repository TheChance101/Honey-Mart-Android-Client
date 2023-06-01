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
include(":user")
include(":admin")
include(":owner")
include(":data")
include(":design_system")
include(":user:ui")
include(":user:domain")
include(":owner:ui")
include(":owner:domain")
include(":admin:domain")
include(":admin:ui")
