package org.the_chance.honeymart.ui.feature.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateConfirmPasswordUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidateFullNameUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidFullNameException
import org.the_chance.honeymart.domain.util.InvalidPasswordException
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createUser: AddUserUseCase,
    private val validateFullName: ValidateFullNameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase,
) : BaseViewModel<SignupUiState, Boolean>(SignupUiState()) {

    override val TAG: String = this::class.simpleName.toString()

    fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateFullName(fullName.toString())
        _state.update { it.copy(fullNameState = fullNameState, fullName = fullName.toString()) }
    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.toString())
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
            { createUser(fullName = fullName, password = password, email = email) },
            ::onSuccess,
            ::onError,
        )
    }

    private fun onSuccess(result: ValidationState) {
        if (result == ValidationState.SUCCESS) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
        }
        _state.update { it.copy(isLoading = false, isSignUp = result) }
    }

    private fun onError(exception: Exception) {
        when (exception) {
            is InvalidEmailException -> {
                _state.update { it.copy(isLoading = false, isError = true) }
                Log.e("TAG", "InvalidEmailException error: ${exception.message}")
            }

            is InvalidFullNameException -> {
                _state.update { it.copy(isLoading = false, isError = true) }
                Log.e("TAG", "InvalidFullNameException error: ${exception.message}")
            }

            is InvalidPasswordException -> {
                _state.update { it.copy(isLoading = false, isError = true) }
                Log.e("TAG", "InvalidPasswordException error: ${exception.message}")
            }

            else -> {
                _state.update { it.copy(isLoading = false, isError = true) }
                Log.e("TAG", "Throwable error: ${exception.message}")
            }
        }
    }



    fun onContinueClicked() {
        val emailState = validateEmail(state.value.email)
        val fullNameState = validateFullName(state.value.fullName)
        if (fullNameState == ValidationState.VALID_FULL_NAME && emailState == ValidationState.VALID_EMAIL) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
        }
        _state.update {
            it.copy(
                emailState = emailState,
                fullNameState = fullNameState,
                isLoading = false
            )
        }

    }

    fun onSignupClicked() {
        val validationState =
            validateConfirmPassword(state.value.password, state.value.confirmPassword)
        if (validationState) {
            addUser(
                fullName = state.value.fullName,
                password = state.value.password,
                email = state.value.email
            )
        }
    }

}