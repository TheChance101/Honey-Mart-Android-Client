object Dependencies {
    // App Ui Dependencies
    val uiDependencies by lazy {
        listOf(
            "androidx.core:core-ktx:${DependencyVersions.CORE_KTX}",
            "androidx.appcompat:appcompat:${DependencyVersions.APP_COMPAT}",
            "com.google.android.material:material:${DependencyVersions.MATERIAL}",
            "androidx.constraintlayout:constraintlayout:${DependencyVersions.CONSTRAINT_LAYOUT}",
            "com.airbnb.android:lottie:${DependencyVersions.LOTTIE}"
        )
    }
    val coreKtxDependency  by lazy { "androidx.core:core-ktx:${DependencyVersions.CORE_KTX}" }
    val dataBindingDependency by lazy { "androidx.databinding:databinding-runtime:${DependencyVersions.DATA_BINDING}" }

    // Test Dependencies
    val junitDependency by lazy { "junit:junit:${DependencyVersions.JUNIT}" }
    val androidTestDependencies by lazy {
        listOf(
            "androidx.test.espresso:espresso-core:${DependencyVersions.ESPRESSO}",
            "androidx.test.ext:junit:${DependencyVersions.ANDROID_JUNIT}"
        )
    }

    // navigation
    val navigationDependencies by lazy {
        listOf(
            "androidx.navigation:navigation-fragment-ktx:${DependencyVersions.ANDROID_NAVIGATION_FRAGMENT}",
            "androidx.navigation:navigation-ui-ktx:${DependencyVersions.ANDROID_NAVIGATION_UI}"
        )
    }

    // Networking Dependencies
    val retrofitDependencies by lazy {
        listOf(
            "com.squareup.retrofit2:retrofit:${DependencyVersions.RETROFIT}",
            "com.squareup.retrofit2:converter-gson:${DependencyVersions.RETROFIT}",
            "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OKHTTP_INTERCEPTOR}",
            "com.google.code.gson:gson:${DependencyVersions.GSON}"
        )
    }
    val glideDependency by lazy {
        "com.github.bumptech.glide:glide:${DependencyVersions.GLIDE}"
    }

    // Coroutines Dependencies
    val coroutinesDependency by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.COROUTINES}"
    }

    // Live Data
    val liveDataDependencies by lazy {
        listOf(
            "androidx.lifecycle:lifecycle-livedata-ktx:${DependencyVersions.LIVE_DATA_LIFE_CYCLE}",
            "androidx.activity:activity-ktx:${DependencyVersions.LIVE_DATA_ACTIVITY}",
            "androidx.fragment:fragment-ktx:${DependencyVersions.LIVE_DATA_FRAGMENT}"
        )
    }

    // Hilt
    val hiltDependency by lazy {
        "com.google.dagger:hilt-android:${DependencyVersions.HILT}"
    }
    val hiltCompiler by lazy {
        "com.google.dagger:hilt-android-compiler:${DependencyVersions.HILT}"
    }
}