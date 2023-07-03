
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Dependencies.Classpath.navigationClasspath)
        classpath("com.android.tools.build:gradle:3.4.0")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
    }
}

plugins {
    id(Plugins.ANDROID_APPLICATION) version PluginsVersions.ANDROID_APPLICATION apply false
    id(Plugins.ANDROID_LIBRARY) version PluginsVersions.ANDROID_LIBRARY apply false
    kotlin(Plugins.KOTLIN_ANDROID) version PluginsVersions.KOTLIN_ANDROID apply false
    id(Plugins.KOTLIN_JVM) version PluginsVersions.KOTLIN_JVM apply false
    kotlin("plugin.serialization") version "1.8.0"

    id(Plugins.HILT_LIBRARY) version PluginsVersions.HILT_LIBRARY apply false

}