plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
}

android {
    namespace = "org.the_chance.honeymart.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

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
        sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
        targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
    }
    kotlinOptions {
        jvmTarget = ConfigData.JAVA_VERSIONS_CODE.toString()
    }
}

dependencies {
    implementation(project(BuildModules.CORE_DOMAIN))
    implementation(Dependencies.coreKtxDependency)
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }
    //retrofit
    Dependencies.retrofitDependencies.forEach { implementation(it) }
    //Coroutine
    implementation(Dependencies.coroutinesDependency)
    //Hilt
    implementation(Dependencies.hiltDependency)
    kapt(Dependencies.hiltCompiler)
}
kapt {
    correctErrorTypes = true
}