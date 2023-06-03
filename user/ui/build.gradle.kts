plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "org.the_chance.ui"
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
    implementation(project(":user:domain"))

    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.constraintLayout)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espresso)

    implementation(Dependencies.dataBinding)

    // Navigation dependency
    implementation(Dependencies.androidNavigationFragment)
    implementation(Dependencies.androidNavigationUi)

    // Glide dependency
    implementation(Dependencies.glide)

    // LiveData dependency
    implementation(Dependencies.liveData)
    implementation(Dependencies.activity)
    implementation(Dependencies.fragment)

}

