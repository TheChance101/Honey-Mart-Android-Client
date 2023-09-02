package org.the_chance.honeymart.ui.feature.authentication.login

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidationUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.signup.FieldState
import org.the_chance.honeymart.ui.feature.authentication.signup.ValidationToast
import org.the_chance.honeymart.util.StringDictionary
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUser: LoginUserUseCase,
    private val validation: ValidationUseCase,
    private val stringResource: StringDictionary,
) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()), LoginInteractionListener {

    override val TAG: String = this::class.java.simpleName
    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginUser(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(unit: Unit) {
        _state.update { it.copy(isLoading = false, isError = false, error = null) }
        effectActionExecutor(_effect, LoginUiEffect.ClickLoginEffect)
    }

    private fun onLoginError(error: ErrorHandler) {
        Log.i("onLoginError: ", error.toString())
        _state.update { it.copy(isLoading = false,isError = true, error = error, isButtonEnabled = true) }
        if (error == ErrorHandler.AlreadyExist) {
            showValidationToast(
                message = stringResource.errorString.getOrDefault(
                    error,
                    ""
                )
            )
        } else {
            showValidationToast(
                message = stringResource.errorString.getOrDefault(
                    error,
                    ""
                )
            )
        }
    }

    override fun onClickLogin() {
        _state.update { it.copy(isButtonEnabled = false) }
        val validState =
            state.value.emailState.value.isNotBlank() && state.value.passwordState.value.isNotBlank()
        if (validState) {
            login(
                email = state.value.emailState.value, password = state.value.passwordState.value
            )
        } else {
            _state.update {
                it.copy(
                    validationToast = ValidationToast(
                        isShow = true, message = stringResource.requiredFieldsMessageString
                    ), isButtonEnabled = true
                )
            }
            showValidationToast(stringResource.requiredFieldsMessageString)
        }
    }

    override fun onClickSignup() {
        effectActionExecutor(_effect, LoginUiEffect.ClickSignUpEffect)
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validation.validateEmail(email.trim().toString())
        _state.update {
            it.copy(
                emailState = FieldState(
                    value = email.toString(),
                    errorState = stringResource.validationString.getOrDefault(emailState, ""),
                    isValid = emailState == ValidationState.VALID_EMAIL
                ),
            )
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validation.validationPassword(password.trim().toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    value = password.toString(),
                    errorState = stringResource.validationString.getOrDefault(passwordState,""),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }

    private fun showValidationToast(message: String) {
        Log.i("onLoginError: ", message.toString())
        _state.update {
            it.copy(
                validationToast = state.value.validationToast.copy(
                    isShow = true,
                    message = message
                )
            )
        }
        effectActionExecutor(_effect, LoginUiEffect.ShowToastEffect)
    }
}