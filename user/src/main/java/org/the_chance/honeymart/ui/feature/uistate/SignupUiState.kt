package org.the_chance.honeymart.ui.feature.uistate

data class SignupUiState(
    val isLoading: Boolean = true,
    val error: List<String> = emptyList(),
    val isSignUp: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val signupDetailsUiState: SignupDetailsUiState = SignupDetailsUiState(),

    )

data class SignupDetailsUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)