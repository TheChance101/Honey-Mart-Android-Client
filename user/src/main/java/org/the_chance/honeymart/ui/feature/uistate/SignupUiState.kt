package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.util.ValidationState

data class SignupUiState(
    val isLoading: Boolean = true,
    val error: List<String> = emptyList(),
    val isSignUp: Boolean = false,
    val validationState: ValidationState = ValidationState.SUCCESS,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val confirmPassword: String = "",

    )