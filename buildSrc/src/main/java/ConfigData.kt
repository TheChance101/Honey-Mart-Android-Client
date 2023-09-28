import org.gradle.api.JavaVersion

object ConfigData{
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    val JAVA_VERSIONS_CODE = JavaVersion.VERSION_17
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}