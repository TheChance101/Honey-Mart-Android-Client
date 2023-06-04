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
    BuildModules.USER_APP,
    BuildModules.ADMIN_APP,
    BuildModules.OWNER_APP,
    BuildModules.CORE,
    BuildModules.DESIGN_SYSTEM
)
