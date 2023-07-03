plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
    id(Plugins.SERIALIZATION)
}

android {
    namespace = "org.the_chance.honeymart.data"
    compileSdk = ConfigData.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = ConfigData.MIN_SDK_VERSION
        targetSdk = ConfigData.TARGET_SDK_VERSION

        testInstrumentationRunner = ConfigData.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
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
    // DataStore
    implementation(Dependencies.dateStoreDependency)
    val ktor_version = "2.3.2"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OKHTTP_INTERCEPTOR}")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}
kapt {
    correctErrorTypes = true
}