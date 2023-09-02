package org.the_chance.honeymart.ui.feature.authentication.signup

import org.the_chance.honeymart.domain.util.ErrorHandler

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isSignUp: Boolean = false,
    val isLogin: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isSignupFirstStepDone: Boolean = false,
    val isAuthScreenVisible: Boolean = true,

    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val confirmPasswordState: FieldState = FieldState(),
    val fullNameState: FieldState = FieldState(),

    val validationToast: ValidationToast = ValidationToast(),
)

data class FieldState(
    val value: String = "",
    val errorState: String = "",
    val isValid: Boolean = errorState.isNotEmpty()
)

data class ValidationToast(
    val isShow: Boolean = false,
    val message: String = "Please fill all required fields"
)

fun SignupUiState.correctValidationFullNameAndEmail(): Boolean {
    return this.fullNameState.isValid && this.emailState.isValid
}

fun SignupUiState.correctValidationPasswordAndConfirmPassword(): Boolean {
    return this.passwordState.isValid && this.confirmPasswordState.isValid
}