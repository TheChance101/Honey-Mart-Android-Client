plugins {
    id(Plugins.JAVA_LIBRARY)
    id(Plugins.KOTLIN_JVM)
    id(Plugins.KOTLIN_KSP)
}

java {
    sourceCompatibility = ConfigData.JAVA_VERSIONS_CODE
    targetCompatibility = ConfigData.JAVA_VERSIONS_CODE
}
dependencies {
    implementation (Dependencies.daggerAndroid)
    implementation(Dependencies.coroutinesDependency)

}
