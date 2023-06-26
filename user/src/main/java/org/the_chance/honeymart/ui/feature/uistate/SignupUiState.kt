package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.util.ValidationState

data class SignupUiState(
    val isLoading: Boolean = false,
    val error: Int = 0,
    val isError: Boolean = false,
    val isSignUp: Boolean = false,
    val isLogin: ValidationState = ValidationState.SUCCESS,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailState: ValidationState = ValidationState.VALID_EMAIL,
    val passwordState: ValidationState = ValidationState.VALID_PASSWORD,
    val confirmPasswordState: ValidationState = ValidationState.VALID_PASSWORD,
    val fullNameState: ValidationState = ValidationState.VALID_FULL_NAME,
)