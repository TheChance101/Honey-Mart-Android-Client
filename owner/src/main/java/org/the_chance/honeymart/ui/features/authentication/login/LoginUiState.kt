package org.the_chance.honeymart.ui.features.authentication.login

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.features.authentication.signup.FieldState
import org.the_chance.honeymart.ui.features.authentication.signup.ValidationToast

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val authLoading:Boolean = false,
    val error: ErrorHandler? = null,
    val isButtonEnabled: Boolean = true,

    val emailState: FieldState = FieldState(),
    val passwordState: FieldState = FieldState(),
    val validationToast: ValidationToast = ValidationToast()
)
