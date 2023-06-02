plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "org.the_chance.domain"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation (project(":data"))
    implementation (Dependencies.coreKtx)
    implementation (Dependencies.coreKtx)
    implementation (Dependencies.junit)
    androidTestImplementation (Dependencies.androidJunit)
    androidTestImplementation (Dependencies.espresso)
}