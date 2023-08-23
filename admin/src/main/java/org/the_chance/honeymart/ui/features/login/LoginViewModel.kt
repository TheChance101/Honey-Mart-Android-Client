package org.the_chance.honeymart.ui.features.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.LoginAdminUseCase
import org.the_chance.honeymart.domain.usecase.ValidationAdminLoginFieldsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginAdminUseCase: LoginAdminUseCase,
    private val validationAdminLogin: ValidationAdminLoginFieldsUseCase

) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()),
    LoginInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private fun loginAdmin(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginAdminUseCase(email, password) },
            { onLoginSuccess() },
            ::onLoginError,
        )
    }

    private fun onLoginSuccess() {
        _state.update { it.copy(isLoading = false) }
        effectActionExecutor(_effect, LoginUiEffect.ClickLoginEffect)
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onClickLogin() {
        val validationState = state.value.emailState.isValid &&
                state.value.passwordState.isValid

        if (validationState) {
            loginAdmin(
                email = state.value.emailState.value,
                password = state.value.passwordState.value
            )
        } else {
            effectActionExecutor(_effect, LoginUiEffect.ShowValidationToastEffect)
        }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validationAdminLogin.validateEmail(email.trim().toString())
        _state.update {
            it.copy(
                emailState = FieldState(
                    errorState = when (emailState) {
                        ValidationState.BLANK_EMAIL -> "email shouldn't be empty"
                        ValidationState.INVALID_EMAIL -> "Invalid email"
                        else -> ""
                    },
                    value = email.toString(),
                    isValid = emailState == ValidationState.VALID_EMAIL
                ),
            )
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validationAdminLogin.validatePassword(password.toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    errorState = when (passwordState) {
                        ValidationState.BLANK_PASSWORD -> "Password shouldn't be empty"
                        ValidationState.INVALID_PASSWORD_LENGTH ->
                            "Password length must be at least 4"
                        else -> ""
                    },
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }
}