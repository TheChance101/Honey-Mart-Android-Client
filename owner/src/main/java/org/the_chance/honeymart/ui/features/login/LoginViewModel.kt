package org.the_chance.honeymart.ui.features.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.LoginOwnerUseCase
import org.the_chance.honeymart.domain.usecase.ValidationLoginFieldsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.signup.FieldState
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginOwnerUseCase: LoginOwnerUseCase,
    private val validationLoginFieldsUseCase: ValidationLoginFieldsUseCase

) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()),
    LoginInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private fun loginOwner(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginOwnerUseCase(email, password) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(isLogin: Boolean) {
        _state.update { it.copy(isLoading = false, isLogin = isLogin) }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onClickLogin() {
        val validationState = state.value.emailState.isValid &&
                state.value.passwordState.isValid

        if (validationState) {
            loginOwner(
                email = state.value.emailState.value,
                password = state.value.passwordState.value
            )
            if (state.value.isLogin) {
                effectActionExecutor(_effect, LoginUiEffect.ClickLoginEffect)
            }
        } else {
            effectActionExecutor(_effect, LoginUiEffect.ShowValidationToastEffect)
        }
    }

    override fun onClickSignup() {
        effectActionExecutor(_effect, LoginUiEffect.ClickSignUpEffect)
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validationLoginFieldsUseCase.validateEmail(email.trim().toString())
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
        val passwordState = validationLoginFieldsUseCase.validatePassword(password.toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    errorState = when (passwordState) {
                        ValidationState.BLANK_PASSWORD -> "Password shouldn't be empty"
                        ValidationState.INVALID_PASSWORD_LENGTH ->
                            "Password length must be at least 8"

                        ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER ->
                            "Please write at least 1 special character"

                        ValidationState.PASSWORD_REGEX_ERROR_DIGIT ->
                            "Please write at least 1 digit"

                        ValidationState.PASSWORD_REGEX_ERROR_LETTER ->
                            "Please write at least 1 letter"

                        else -> ""
                    },
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }
}