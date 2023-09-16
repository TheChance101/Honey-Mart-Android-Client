plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.HILT_LIBRARY)
    id(Plugins.KSP)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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

    androidComponents.onVariants { variant ->
        val name = variant.name
        sourceSets {
            getByName(name).kotlin.srcDir("${buildDir.absolutePath}/generated/ksp/${name}/kotlin")
        }
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
    kotlinOptions {
        jvmTarget = ConfigData.JAVA_VERSIONS_CODE.toString()
    }
     buildFeatures {
        dataBinding = true
         compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
}

dependencies {
    implementation(project(BuildModules.DESIGN_SYSTEM))
    implementation(project(BuildModules.CORE_DOMAIN))
    implementation(project(BuildModules.CORE_DATA))
    Dependencies.firebaseDependency.forEach { implementation(it) }
    Dependencies.uiDependencies.forEach { implementation(it) }
    Dependencies.composeDependency.forEach { implementation(it) }
    Dependencies.ktorDependency.forEach { implementation(it) }
    testImplementation(Dependencies.junitDependency)
    Dependencies.androidTestDependencies.forEach { androidTestImplementation(it) }
    implementation(platform(Dependencies.composePlatformBom))
    androidTestImplementation(platform(Dependencies.composePlatformBomAndroidTest))
    //Navigation
    Dependencies.navigationDependencies.forEach { implementation(it) }
    //retrofit
    Dependencies.retrofitDependencies.forEach { implementation(it) }
    //Hilt
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltDependency)
    // FCM notification
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-messaging-ktx")
    //paging
    implementation(Dependencies.pagingCommon)
    implementation(Dependencies.pagingRuntime)
    implementation(Dependencies.pagingCompose)
    //splash
    implementation(Dependencies.splashDependency)
    debugImplementation(Dependencies.composeUiDependency)
    //Permission
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")
    //Arrow
    implementation(Dependencies.arrowCore)
    implementation(Dependencies.arrowFxCoroutines)
    implementation(Dependencies.arrowOptics)
    ksp(Dependencies.arrowOpticsKSP)
}