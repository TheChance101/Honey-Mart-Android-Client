package org.the_chance.honeymart.ui.feature.authentication.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.RegisterUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateSignupFieldsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.AuthenticationInteractionListener
import org.the_chance.honeymart.util.StringDictionary
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase,
    private val loginUser: LoginUserUseCase,
    private val validation: ValidateSignupFieldsUseCase,
    private val stringResource: StringDictionary,
) : BaseViewModel<SignupUiState, SignupUiEffect>(SignupUiState()),
    SignupInteractionListener, AuthenticationInteractionListener {

    override val TAG: String = this::class.simpleName.toString()

    override fun onClickOnBoardingSignUp() {
        effectActionExecutor(_effect, SignupUiEffect.ClickOnboardingSignupEffect)
    }

    override fun onClickOnBoardingLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickContinue() {
        if (!_state.value.correctValidationFullNameAndEmail()) {
            showValidationToast(stringResource.requiredFieldsMessageString)
        }
    }

    override fun onClickSignup() {
        _state.update { it.copy(isButtonEnabled = false) }
        if (state.value.correctValidationPasswordAndConfirmPassword()) {
            createUser(
                fullName = state.value.fullNameState.value,
                password = state.value.passwordState.value,
                email = state.value.emailState.value
            )
        } else {
            _state.update { it.copy(isButtonEnabled = true) }
            showValidationToast(stringResource.requiredFieldsMessageString)
        }
    }

    private fun createUser(fullName: String, password: String, email: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                registerUser(
                    fullName = fullName,
                    password = password,
                    email = email
                )
            },
            ::onCreateUserSuccess,
            ::onCreateUserError,
        )
    }

    private fun onCreateUserSuccess(isSignUp: Boolean) {
        login(email = _state.value.emailState.value, password = _state.value.passwordState.value)
        _state.update { it.copy(isSignUp = isSignUp) }
    }

    private fun onCreateUserError(error: ErrorHandler) {
        _state.update { it.copy(error = error) }
        val errorMessage = stringResource.errorString.getOrDefault(error, "")
        showValidationToast(message = errorMessage)
        if (error == ErrorHandler.EmailAlreadyExist) {
            _state.update {
                it.copy(emailState = state.value.emailState.copy(errorState = errorMessage))
            }
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

    private fun onLoginSuccess(unit: Unit) {
        _state.update { it.copy(isLoading = false, isLogin = true) }
        effectActionExecutor(_effect, SignupUiEffect.ClickSignupEffect)
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isButtonEnabled = true) }
        showValidationToast(
            message = stringResource.errorString.getOrDefault(
                error,
                ""
            )
        )
    }

    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validation.validationFullName(fullName.trim().toString())
        _state.update {
            it.copy(
                fullNameState = FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        fullNameState,
                        ""
                    ),
                    value = fullName.toString(),
                    isValid = fullNameState == ValidationState.VALID_FULL_NAME
                )
            )
        }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validation.validateEmail(email.trim().toString())
        _state.update {
            it.copy(
                emailState = FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        emailState,
                        ""
                    ),
                    value = email.toString(),
                    isValid = emailState == ValidationState.VALID_EMAIL
                ),
                error = null
            )
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validation.validationPassword(password.toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        passwordState,
                        ""
                    ),
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState = validation.validateConfirmPassword(
            _state.value.passwordState.value,
            confirmPassword.toString()
        )
        if (passwordState == ValidationState.CONFIRM_PASSWORD_DOES_NOT_MATCH) {
            _state.update {
                it.copy(
                    confirmPasswordState = FieldState(
                        errorState = stringResource.validationString.getOrDefault(
                            passwordState,
                            ""
                        ),
                        value = confirmPassword.toString(),
                        isValid = false
                    )
                )
            }
        } else {
            _state.update {
                it.copy(
                    confirmPasswordState = FieldState(
                        errorState = "",
                        value = confirmPassword.toString(),
                        isValid = true
                    )
                )
            }
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
        effectActionExecutor(_effect, SignupUiEffect.ShowToastEffect)
    }

}