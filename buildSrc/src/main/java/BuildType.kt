interface BuildType {
    companion object {
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}