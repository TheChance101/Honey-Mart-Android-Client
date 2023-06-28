package org.the_chance.honeymart.ui.feature.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddUserUseCase
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateConfirmPasswordUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidateFullNameUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createAccount: AddUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val validateFullName: ValidateFullNameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase,
) : BaseViewModel<SignupUiState, AuthUiEffect>(SignupUiState()) {

    override val TAG: String = this::class.simpleName.toString()

    private lateinit var args: SignupFragmentArgs

    fun saveArgs(args: SignupFragmentArgs){
        args.also { this.args = it }
    }

    fun getData() {
        onClickSignup()
    }

    fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateFullName(fullName.trim().toString())
        _state.update { it.copy(fullNameState = fullNameState, fullName = fullName.trim().toString()) }
    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.trim().toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

    fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState =
            validateConfirmPassword(state.value.password, confirmPassword.toString())
        if (!passwordState) {
            _state.update { it.copy(confirmPasswordState = ValidationState.INVALID_CONFIRM_PASSWORD) }
        } else {
            _state.update {
                it.copy(
                    confirmPasswordState = ValidationState.VALID_PASSWORD,
                    confirmPassword = confirmPassword.toString()
                )
            }
        }
    }

    private fun addUser(fullName: String, password: String, email: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { createAccount(fullName = fullName, password = password, email = email) },
            ::onSuccess,
            ::onError,
        )
    }

    private fun onSuccess(isSignUp: Boolean) {
        if (isSignUp) {
            login(email = _state.value.email, password = _state.value.password)
        }
        _state.update { it.copy(isLoading = false, isSignUp = isSignUp) }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginUser(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(loginState: ValidationState) {
        if (loginState == ValidationState.SUCCESS) {
            viewModelScope.launch {
                _effect.emit(EventHandler(AuthUiEffect.ClickSignUpEffect(args.AuthData)))
            }
        }
        _state.update { it.copy(isLoading = false, isLogin = loginState) }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    fun onContinueClicked() {
        val emailState = validateEmail(state.value.email.trim())
        val fullNameState = validateFullName(state.value.fullName.trim())
        if (fullNameState == ValidationState.VALID_FULL_NAME && emailState == ValidationState.VALID_EMAIL) {
            viewModelScope.launch {
                _effect.emit(EventHandler(AuthUiEffect.ClickContinueEffect))
            }
        }
        _state.update {
            it.copy(emailState = emailState, fullNameState = fullNameState, isLoading = false)
        }
    }

    fun onClickSignup() {
        val validationState =
            validateConfirmPassword(state.value.password, state.value.confirmPassword)
        if (validationState) {
            addUser(
                fullName = state.value.fullName, password = state.value.password,
                email = state.value.email
            )
        }
    }

}