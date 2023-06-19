package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.util.ValidationState

data class SignupUiState(
    val isLoading: Boolean = true,
    val error: List<String> = emptyList(),
    val isError: Boolean = false,
    val isSignUp: ValidationState = ValidationState.SUCCESS,
    val fullName: ValidationState = ValidationState.VALID_FULL_NAME,
    val fullNameInput: String = "",
    val emailInput: String = "",
    val email: ValidationState = ValidationState.VALID_EMAIL,
    val password: ValidationState = ValidationState.VALID_EMAIL,
    val confirmPassword: ValidationState = ValidationState.INVALID_CONFIRM_PASSWORD,

    )