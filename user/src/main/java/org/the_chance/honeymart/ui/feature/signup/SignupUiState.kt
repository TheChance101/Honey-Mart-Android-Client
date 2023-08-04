package org.the_chance.honeymart.ui.feature.signup

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isSignUp: Boolean = false,
    val isLogin: ValidationState = ValidationState.INVALID_CONFIRM_PASSWORD,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailState: ValidationState = ValidationState.VALID_EMAIL,
    val passwordState: ValidationState = ValidationState.VALID_PASSWORD,
    val confirmPasswordState: ValidationState = ValidationState.VALID_PASSWORD,
    val fullNameState: ValidationState = ValidationState.VALID_FULL_NAME,
    val showToast:Boolean = false
)


fun SignupUiState.continueValidation(): Boolean {
    return this.fullNameState == ValidationState.VALID_FULL_NAME
            && this.emailState == ValidationState.VALID_EMAIL &&
            this.email.isNotEmpty() && this.fullName.isNotEmpty()
}