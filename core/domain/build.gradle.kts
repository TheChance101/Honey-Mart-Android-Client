plugins {
    id(Plugins.JAVA_LIBRARY)
    id(Plugins.KOTLIN_JVM)
    kotlin(Plugins.KOTLIN_KAPT)
}

java {
    sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
    targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
}
dependencies {
    implementation (Dependencies.daggerAndroid)
    implementation(Dependencies.coroutinesDependency)
    // Paging
    implementation(Dependencies.pagingCommon)
    // Required for Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation ("org.mockito:mockito-junit-jupiter:3.11.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}
tasks.withType<Test> {
    useJUnitPlatform()
}