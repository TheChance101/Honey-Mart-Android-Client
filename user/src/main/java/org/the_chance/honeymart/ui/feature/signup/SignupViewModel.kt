package org.the_chance.honeymart.ui.feature.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddUserUseCase
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateConfirmPasswordUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidateFullNameUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationUiEffect
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createAccount: AddUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val validateFullName: ValidateFullNameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase,
) : BaseViewModel<SignupUiState, AuthenticationUiEffect>(SignupUiState()), SignupInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateFullName(fullName.trim().toString())
        _state.update { it.copy(fullNameState = fullNameState, fullName = fullName.toString()) }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.trim().toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState =
            validateConfirmPassword(state.value.password, confirmPassword.toString())
        if (!passwordState) {
            _state.update {
                it.copy(
                    confirmPasswordState = ValidationState.INVALID_CONFIRM_PASSWORD,
                    confirmPassword = confirmPassword.toString()
                )
            }
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
        _state.update {
            it.copy(
                isLoading = false, error = error,
                fullNameState = ValidationState.INVALID_FULL_NAME,
                emailState = ValidationState.INVALID_EMAIL
            )
        }
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
        _state.update { it.copy(isLoading = false, isLogin = loginState) }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onClickSignup() {
        val validationState =
            validateConfirmPassword(state.value.password, state.value.confirmPassword)
        if (validationState) {
            addUser(
                fullName = state.value.fullName, password = state.value.password,
                email = state.value.email
            )
            _state.update {
                it.copy(showToast = false)
            }
        } else {
            _state.update {
                it.copy(showToast = true)
            }
        }
    }

}