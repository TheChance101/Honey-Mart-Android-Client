package org.the_chance.honeymart.ui.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationUiEffect
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loginUser: LoginUserUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginUiState, AuthenticationUiEffect>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName

    private val authData = LoginFragmentArgs.fromSavedStateHandle(savedStateHandle).authData


    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginUser(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    fun getData() {
        onLoginClick()
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        if (validationState == ValidationState.SUCCESS) {
            viewModelScope.launch {
                _effect.emit(EventHandler(AuthenticationUiEffect.ClickLoginEffect(authData)))
            }
        }
        _state.update {
            it.copy(isLoading = false, error = null, validationState = validationState)
        }

    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error
            )
        }
    }


    fun onLoginClick() {
        if (_state.value.emailState == ValidationState.VALID_EMAIL ||
            _state.value.passwordState == ValidationState.VALID_PASSWORD
        ) {
            login(_state.value.email.trim(), _state.value.password.trim())

        }
    }

    fun onClickSignUp() {
        viewModelScope.launch {
            _effect.emit(EventHandler(AuthenticationUiEffect.ClickSignUpEffect(authData)))
        }
    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.trim().toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.trim().toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

}