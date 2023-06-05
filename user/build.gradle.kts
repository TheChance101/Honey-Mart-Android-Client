plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "org.the_chance.user"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "org.the_chance.user"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":design_system"))
    implementation(project(":core"))
    Dependencies.uiDependencies.forEach { implementation(it) }
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }
    implementation(Dependencies.dataBindingDependency)
    // Navigation dependency
    Dependencies.navigationDependencies.forEach { implementation(it) }
    // Glide dependency
    implementation(Dependencies.glideDependency)
    //Hilt dependency
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltDependency)
    // LiveData dependency
    Dependencies.liveDataDependencies.forEach { implementation(it) }
}
