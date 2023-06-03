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
    //Hilt dependency
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hilt)
    // LiveData dependency
    implementation(Dependencies.liveData)
    implementation(Dependencies.activity)
    implementation(Dependencies.fragment)

}
