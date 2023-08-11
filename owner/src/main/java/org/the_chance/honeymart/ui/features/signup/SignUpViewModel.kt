package org.the_chance.honeymart.ui.features.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddOwnerUseCase
import org.the_chance.honeymart.domain.usecase.ValidationSignupFieldsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoInteractionsListener
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createAccount: AddOwnerUseCase,
    private val validationSignupFields: ValidationSignupFieldsUseCase
) : BaseViewModel<SignupUiState, SignupUiEffect>(SignupUiState()), SignupInteractionListener,
    MarketInfoInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    override fun onClickLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickSignup() {
        val validationState = state.value.emailState.isValid &&
                state.value.passwordState.isValid &&
                state.value.fullNameState.isValid &&
                state.value.confirmPasswordState.isValid

        if (validationState) {
            // navigate to loginScreen
            _state.update {
                it.copy(showToast = false)
            }
        } else {
            _state.update {
                it.copy(showToast = true)
            }
        }
    }

    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validationSignupFields.validationFullName(
            fullName.trim().toString()
        )
        _state.update {
            it.copy(
                fullNameState = FieldState(
                    errorState = when (fullNameState) {
                        ValidationState.BLANK_FULL_NAME -> "name shouldn't be empty"
                        ValidationState.INVALID_FULL_NAME -> "Invalid name"
                        else -> ""
                    },
                    value = fullName.toString(),
                    isValid = fullNameState == ValidationState.VALID_FULL_NAME
                ),
            )
        }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validationSignupFields.validateEmail(email.trim().toString())
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
        val passwordState = validationSignupFields.validationPassword(password.toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    errorState = when (passwordState) {
                        ValidationState.BLANK_PASSWORD -> "Password shouldn't be empty"
                        ValidationState.INVALID_PASSWORD_LENGTH -> "Password length must be at least 8"
                        ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER -> "Please write at least 1 special character"
                        ValidationState.PASSWORD_REGEX_ERROR_LETTER -> "Please write at least 1 letter"
                        ValidationState.PASSWORD_REGEX_ERROR_DIGIT -> "Please write at least 1 digit"
                        else -> ""
                    },
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState = validationSignupFields.validateConfirmPassword(
            state.value.passwordState.value,
            confirmPassword.toString()
        )
        if (!passwordState) {
            _state.update {
                it.copy(
                    confirmPasswordState = FieldState(
                        errorState = "password doesn't match",
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


    private fun addOwner(
        fullName: String,
        email: String,
        password: String,
    ) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                createAccount(
                    fullName = fullName,
                    password = password,
                    email = email,
                )
            },
            ::onSuccess,
            ::onError,
        )
    }


    private fun onSuccess(isSignUp: Boolean) {
        _state.update { it.copy(isLoading = false, isSignUp = isSignUp) }
        if (isSignUp) {
            // navigate to marketInfo screen
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onClickSendButton() {
    }

    override fun onMarketNameInputChange(marketName: CharSequence) {
    }

    override fun onMarketAddressInputChange(address: CharSequence) {
    }

    override fun onDescriptionInputChanged(description: CharSequence) {
    }
}