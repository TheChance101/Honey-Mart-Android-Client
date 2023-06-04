object Dependencies {
    private val versions = DependencyVersions

    val testDependencies by lazy {
        listOf(
            "junit:junit:${versions.JUNIT}",
            "androidx.test.espresso:espresso-core:${versions.ESPRESSO}",
            "androidx.test.ext:junit:${versions.ANDROID_JUNIT}"
        )
    }
    val navigationDependencies by lazy {
        listOf(
            "androidx.navigation:navigation-fragment-ktx:${versions.ANDROID_NAVIGATION_FRAGMENT}",
            "androidx.navigation:navigation-ui-ktx:${versions.ANDROID_NAVIGATION_UI}"
        )
    }
    val uiDependencies by lazy {
        listOf(
            "androidx.core:core-ktx:${versions.CORE_KTX}",
            "androidx.appcompat:appcompat:${versions.APP_COMPAT}",
            "com.google.android.material:material:${versions.MATERIAL}",
            "androidx.constraintlayout:constraintlayout:${versions.CONSTRAINT_LAYOUT}"
        )
    }
    val retrofitDependencies by lazy {
        listOf(
            "com.squareup.retrofit2:retrofit:${versions.RETROFIT}",
            "com.squareup.retrofit2:converter-gson:${versions.RETROFIT}",
            "com.squareup.okhttp3:logging-interceptor:${versions.OKHTTP_INTERCEPTOR}",
            "com.google.code.gson:gson:${versions.GSON}"
        )
    }
    val coroutines by lazy {
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.COROUTINES}"
    }
    val glide by lazy {
        "com.github.bumptech.glide:glide:${versions.GLIDE}"
    }
    val liveDataDependencies by lazy {
        listOf(
            "androidx.lifecycle:lifecycle-livedata-ktx:${versions.LIVE_DATA}",
            "androidx.activity:activity-ktx:${versions.ACTIVITY}",
            "androidx.fragment:fragment-ktx:${versions.FRAGMENT}"
        )
    }
    val hiltDependencies by lazy {
        listOf(
            "com.google.dagger:hilt-android:${versions.HILT}",
            "com.google.dagger:hilt-android-compiler:${versions.HILT}"
        )
    }
}
