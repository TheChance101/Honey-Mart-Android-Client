package org.the_chance.honeymart.ui.feature.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.RegisterUserUseCase
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidationUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationInteractionListener
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createAccount: RegisterUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val validation: ValidationUseCase,
) : BaseViewModel<SignupUiState, SignupUiEffect>(SignupUiState()),
    SignupInteractionListener, AuthenticationInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validation.validationFullName(fullName.trim().toString())
        _state.update { it.copy(fullNameState = fullNameState, fullName = fullName.toString()) }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validation.validateEmail(email.trim().toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validation.validationPassword(password.toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState =
            validation.validateConfirmPassword(state.value.password, confirmPassword.toString())
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
        _state.update { it.copy(isLoading = true, isSignUp = isSignUp) }
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
        if (loginState == ValidationState.SUCCESS)
            effectActionExecutor(_effect, SignupUiEffect.ClickSignupEffect)

        _state.update { it.copy(isLoading = false, isLogin = loginState) }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onClickLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickSignup() {
        val validationState =
            validation.validateConfirmPassword(state.value.password, state.value.confirmPassword)
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

    override fun onClickOnBoardingSignUp() {
        effectActionExecutor(_effect, SignupUiEffect.ClickOnBoardingSignUp)
    }

    override fun onClickOnBoardingLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickOnBoardingLogin)
    }

}