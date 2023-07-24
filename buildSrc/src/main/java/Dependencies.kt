object Dependencies {
    // App Ui Dependencies
    val uiDependencies by lazy {
        listOf(
            "androidx.core:core-ktx:${DependencyVersions.CORE_KTX}",
            "androidx.appcompat:appcompat:${DependencyVersions.APP_COMPAT}",
            "com.google.android.material:material:${DependencyVersions.MATERIAL}",
            "androidx.constraintlayout:constraintlayout:${DependencyVersions.CONSTRAINT_LAYOUT}",
            "com.airbnb.android:lottie:${DependencyVersions.LOTTIE}",
            "androidx.slidingpanelayout:slidingpanelayout:${DependencyVersions.SLIDE_PANE}"
        )
    }
    val coreKtxDependency by lazy { "androidx.core:core-ktx:${DependencyVersions.CORE_KTX}" }
    val dataBindingDependency by lazy { "androidx.databinding:databinding-runtime:${DependencyVersions.DATA_BINDING}" }

    // Test Dependencies
    val junitDependency by lazy { "junit:junit:${DependencyVersions.JUNIT}" }
    val androidTestDependencies by lazy {
        listOf(
            "androidx.test.espresso:espresso-core:${DependencyVersions.ESPRESSO}",
            "androidx.test.ext:junit:${DependencyVersions.ANDROID_JUNIT}",
            "androidx.compose.ui:ui-test-junit4"
        )
    }
    val debugmplementation by lazy {
        listOf(
            "androidx.compose.ui:ui-tooling",
            "androidx.compose.ui:ui-test-manifest"
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
            "androidx.fragment:fragment-ktx:${DependencyVersions.LIVE_DATA_FRAGMENT}",
            "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersions.RUN_TIME_LIFECYCLE_COMPOSE}"
        )
    }

    // Hilt
    val hiltDependency by lazy {
        "com.google.dagger:hilt-android:${DependencyVersions.HILT}"
    }
    val hiltCompiler by lazy {
        "com.google.dagger:hilt-android-compiler:${DependencyVersions.HILT}"
    }
    val daggerAndroid by lazy {
        "com.google.dagger:dagger-android:${DependencyVersions.DAGGER_ANDROID}"
    }

    val swipeDependency by lazy {
        "it.xabaras.android:recyclerview-swipedecorator:${DependencyVersions.SWIPE}"
    }
    val coilDependency by lazy {
        "io.coil-kt:coil:${DependencyVersions.COIL}"
    }
    val dateStoreDependency by lazy {
        "androidx.datastore:datastore-preferences:${DependencyVersions.DATA_STORE}"
    }

    val composeDependency by lazy {
        listOf(
            "androidx.compose.material3:material3:${DependencyVersions.MATERIAL3_COMPOSE}",
            "androidx.activity:activity-compose:${DependencyVersions.ACTIVITY_COMPOSE}",
            //  "androidx.compose:compose-bom:${DependencyVersions.BOM_COMPOSE}",
            "androidx.compose.ui:ui",
            "androidx.compose.ui:ui-graphics",
            "androidx.compose.ui:ui-tooling-preview",
            // "androidx.compose:compose-bom:${DependencyVersions.BOM_COMPOSE}",
            "androidx.compose.foundation:foundation:${DependencyVersions.FOUNDATION_COMPOSE}",
            "androidx.compose.ui:ui-util:${DependencyVersions.UI_UTIL_COMPOSE}",
            "androidx.constraintlayout:constraintlayout-compose:${DependencyVersions.CONSTRAINT_LAYOUT_COMPOSE}",
            "com.google.accompanist:accompanist-systemuicontroller:${DependencyVersions.SYSTEM_UI_CONTROLLER}",
            "io.coil-kt:coil-compose:${DependencyVersions.COIL_COMPOSE}"
        )

    }

    object Classpath {

        val navigationClasspath by lazy {
            "androidx.navigation:navigation-safe-args-gradle-plugin:${DependencyVersions.ClasspathVersions.NAVIGATION_CLASSPATH}"
        }
    }

}