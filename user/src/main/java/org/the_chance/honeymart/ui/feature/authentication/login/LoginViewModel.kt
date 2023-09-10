package org.the_chance.honeymart.ui.feature.authentication.login

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.usecaseManager.user.UserAuthenticationManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.signup.FieldState
import org.the_chance.honeymart.util.StringDictionary
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUser: UserAuthenticationManagerUseCase,
    private val stringResource: StringDictionary,
) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()), LoginInteractionListener {

    override val TAG: String = this::class.java.simpleName
    override fun onClickSignup() {
        effectActionExecutor(_effect, LoginUiEffect.ClickSignUpEffect)
    }

    override fun onClickLogin() {
        _state.update { it.copy(isButtonEnabled = false) }
        if (state.value.validLoginFields()) {
            _state.update {
                it.copy(
                    emailState = state.value.emailState.copy(errorState = ""),
                    passwordState = state.value.passwordState.copy(errorState = "")
                )
            }
            login(email = state.value.emailState.value, password = state.value.passwordState.value)
        } else {
            _state.update { it.copy(isButtonEnabled = true) }
            showValidationToast(stringResource.requiredFieldsMessageString)
        }
    }

    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginUser.loginUseCase(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(unit: Unit) {
        _state.update { it.copy(isLoading = false, isError = false, error = null) }
        effectActionExecutor(_effect, LoginUiEffect.ClickLoginEffect)
    }

    private fun onLoginError(error: ErrorHandler) {
        val errorMessage = stringResource.errorString.getOrDefault(error, "")
        _state.update {
            it.copy(
                isLoading = false,
                isError = true,
                error = error,
                isButtonEnabled = true,
                emailState = _state.value.emailState.copy(errorState = " "),
                passwordState = _state.value.passwordState.copy(errorState = " ")
            )
        }
        showValidationToast(message = errorMessage)
    }


    override fun onEmailInputChange(email: CharSequence) {
        _state.update {
            it.copy(emailState = FieldState(value = email.toString(), errorState = ""))
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        _state.update {
            it.copy(passwordState = FieldState(value = password.toString(), errorState = ""))
        }
    }

    private fun showValidationToast(message: String) {
        _state.update {
            it.copy(
                validationToast = state.value.validationToast.copy(
                    isShow = true,
                    message = message
                ),
                isLoading = false
            )
        }
        effectActionExecutor(_effect, LoginUiEffect.ShowToastEffect)
    }
}