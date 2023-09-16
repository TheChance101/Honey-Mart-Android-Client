// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Dependencies.Classpath.navigationClasspath)
        classpath(Dependencies.Classpath.toolsBuildClasspath)
        classpath(Dependencies.Classpath.googleServiceClasspath)
        classpath(Dependencies.Classpath.googleFirebaseCrashClasspath)
        classpath(Dependencies.Classpath.kotlinSerializationClasspath)
    }
    val compose_version by extra("1.5.0")
}

plugins {
    id(Plugins.ANDROID_APPLICATION) version PluginsVersions.ANDROID_APPLICATION apply false
    id(Plugins.ANDROID_LIBRARY) version PluginsVersions.ANDROID_LIBRARY apply false
    kotlin(Plugins.KOTLIN_ANDROID) version PluginsVersions.KOTLIN_ANDROID apply false
    id(Plugins.KOTLIN_JVM) version PluginsVersions.KOTLIN_JVM apply false
    kotlin(Plugins.PLUGIN_SERIALIZATION) version PluginsVersions.PLUGIN_SERIALIZATION
    id(Plugins.HILT_LIBRARY) version PluginsVersions.HILT_LIBRARY apply false
    id(Plugins.KSP) version PluginsVersions.KSP apply false
}