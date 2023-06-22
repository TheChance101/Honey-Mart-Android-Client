package org.the_chance.honeymart.ui.feature.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginUiState, Boolean>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName

    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginUserUseCase(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        if (validationState == ValidationState.SUCCESS) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
        }
        _state.update { it.copy(isLoading = false, error = 1, validationState = validationState) }

    }

    private fun onLoginError(exception: Exception) {
        _state.update { it.copy(isLoading = false, error = 1) }
    }


    fun onLoginClick() {
        if (_state.value.validationState == ValidationState.SUCCESS) {
            viewModelScope.launch { login(_state.value.email, _state.value.password) }
        }

    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

}