plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
}

android {
    namespace = "org.the_chance.user"
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "org.the_chance.user"
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(BuildModules.DESIGN_SYSTEM))
    implementation(project(BuildModules.CORE))
    Dependencies.uiDependencies.forEach { implementation(it) }
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }
    implementation(Dependencies.dataBindingDependency)
    Dependencies.navigationDependencies.forEach { implementation(it) }
    implementation(Dependencies.glideDependency)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltDependency)
    Dependencies.liveDataDependencies.forEach { implementation(it) }
}
