object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val androidJunit by lazy { "androidx.test.ext:junit:${Versions.androidJunit}" }
    val okHttpInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpInterceptor}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val gson by lazy {  "com.google.code.gson:gson:${Versions.gson}" }
    val androidNavigationFragment by lazy{ "androidx.navigation:navigation-fragment-ktx:${Versions.androidNavigationFragment}"}
    val androidNavigationUi by lazy{ "androidx.navigation:navigation-ui-ktx:${Versions.androidNavigationUi}"}
    val glide by lazy{ "com.github.bumptech.glide:glide:${Versions.glide}"}
    val liveData by lazy{ "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"}
    val activity by lazy{ "androidx.activity:activity-ktx:${Versions.activity}"}
    val dataBinding by lazy{"androidx.databinding:databinding-runtime:${Versions.dataBinding}"}
    val fragment by lazy{ "androidx.fragment:fragment-ktx:${Versions.fragment}"}
    val hilt by lazy{ "com.google.dagger:hilt-android:${Versions.hilt}"}
}


