plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin(Plugins.KOTLIN_KAPT)
}

java {
    sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
    targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
}
dependencies {
    implementation ("com.google.dagger:dagger-android:2.35.1")
}
