plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "org.the_chance.admin"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "org.the_chance.admin"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":design_system"))
    implementation(project(":core"))
    Dependencies.uiDependencies.forEach { implementation(it) }
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }

}