plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
}

android {
    namespace = "org.the_chance.owner"
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "org.the_chance.owner"
        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION
        versionCode = ConfigData.VERSION_CODE
        versionName = ConfigData.VERSION_NAME

        testInstrumentationRunner = ConfigData.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
        targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
    }
    kotlinOptions {
        jvmTarget = ConfigData.JAVA_VERSIONS_CODE.toString()
    }
}

dependencies {
    implementation(project(BuildModules.DESIGN_SYSTEM))
    implementation(project(BuildModules.CORE))
    Dependencies.uiDependencies.forEach { implementation(it) }
    Dependencies.testDependencies.forEach { testImplementation(it) }
    Dependencies.navigationDependencies.forEach { implementation(it) }
    Dependencies.hiltDependencies.forEach { implementation(it) }
    Dependencies.liveDataDependencies.forEach { implementation(it) }
    implementation(Dependencies.glide)
}