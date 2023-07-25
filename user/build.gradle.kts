plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
    id(Plugins.NAVIGATION_ARGS)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id(Plugins.SERIALIZATION)
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
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
        targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
    kotlinOptions {
        jvmTarget = ConfigData.JAVA_VERSIONS_CODE.toString()
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(BuildModules.DESIGN_SYSTEM))
    implementation(project(BuildModules.CORE_DOMAIN))
    implementation(project(BuildModules.CORE_DATA))
    implementation("com.google.firebase:firebase-crashlytics:18.3.2")
    implementation("com.google.firebase:firebase-analytics:21.2.0")
    Dependencies.uiDependencies.forEach { implementation(it) }
    Dependencies.composeDependency.forEach { implementation(it) }
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }
    //Navigation
    Dependencies.navigationDependencies.forEach { implementation(it) }
    //Glide
    implementation(Dependencies.glideDependency)
    //Hilt
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltDependency)
    //LiveData
    Dependencies.liveDataDependencies.forEach { implementation(it) }
    //retrofit
    Dependencies.retrofitDependencies.forEach { implementation(it) }
    //swipe
    implementation(Dependencies.swipeDependency)
    // coil
    implementation(Dependencies.coilDependency)
    //splash
    implementation("androidx.core:core-splashscreen:1.0.1")
    val ktor_version = "2.3.2"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("io.ktor:ktor-client-logging-jvm:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OKHTTP_INTERCEPTOR}")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")





}
