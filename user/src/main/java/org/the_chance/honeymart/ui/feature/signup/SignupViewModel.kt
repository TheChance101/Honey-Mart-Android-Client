package org.the_chance.honeymart.ui.feature.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    val passwordInput = MutableStateFlow("")
    val confirmPasswordInput = MutableStateFlow("")

    init {

        setPassword()
        setConfirmPassword()
    }


    private fun setPassword() {
        viewModelScope.launch {
            passwordInput.collect { password ->
                val passwordState = validatePassword(password)
                _state.update { it.copy(password = passwordState) }
            }
        }
    }

    private fun setConfirmPassword() {
        viewModelScope.launch {
            confirmPasswordInput.collect { confirmPassword ->
                val passwordState = validateConfirmPassword(passwordInput.value, confirmPassword)
                if (!passwordState) {
                    _state.update { it.copy(confirmPassword = ValidationState.INVALID_CONFIRM_PASSWORD) }
                } else {
                    _state.update { it.copy(confirmPassword = ValidationState.VALID_PASSWORD) }
                }
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
        handleException(exception)
        Log.e("TAG", "Throwable error: ${exception.message}")
    }

    private fun handleException(exception: Exception) {
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

    fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateFullName(fullName.toString())
        _state.update { it.copy(fullName = fullNameState, fullNameInput = fullName.toString()) }
    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.toString())
        _state.update { it.copy(email = emailState, emailInput = email.toString()) }
    }

    fun onContinueClicked() {
        val emailState = validateEmail(state.value.emailInput)
        val fullNameState = validateFullName(state.value.fullNameInput)
        if (fullNameState == ValidationState.VALID_FULL_NAME && emailState == ValidationState.VALID_EMAIL) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
            Log.e("TAG", "on click  continue: ")
        }
        _state.update { it.copy(email = emailState, fullName = fullNameState) }

    }

    fun onSignupClicked() {
        val validationState =
            validateConfirmPassword(passwordInput.value, confirmPasswordInput.value)
        if (validationState) {
            addUser(
                fullName = state.value.fullNameInput,
                password = passwordInput.value,
                email = state.value.emailInput
            )
        }
        Log.e("TAG", "on click  signup: ")
    }


}