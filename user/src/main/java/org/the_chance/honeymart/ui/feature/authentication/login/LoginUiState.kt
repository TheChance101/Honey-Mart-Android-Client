package org.the_chance.honeymart.ui.feature.authentication.login

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.authentication.signup.FieldState
import org.the_chance.honeymart.ui.feature.authentication.signup.ValidationToast

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val isButtonEnabled: Boolean = true,
    val validationToast: ValidationToast = ValidationToast(),
)

fun LoginUiState.validLoginFields() =
    this.emailState.value.isNotBlank() && this.passwordState.value.isNotBlank()
