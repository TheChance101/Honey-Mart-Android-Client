package org.the_chance.honeymart.ui.feature.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateConfirmPasswordUseCase
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidFullNameException
import org.the_chance.honeymart.domain.util.InvalidPasswordException
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import org.the_chance.honeymart.util.handleValidation
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createUser: AddUserUseCase,
    private val validateConfirmPassword: ValidateConfirmPasswordUseCase,
) : BaseViewModel<SignupUiState, Boolean>(SignupUiState()) {

    override val TAG: String = this::class.simpleName.toString()

    val fullNameInput = MutableStateFlow("")
    val emailInput = MutableStateFlow("")
    val passwordInput = MutableStateFlow("")
    val confirmPasswordInput = MutableStateFlow("")

    init {
        setEmail()
        setFullName()
        setPassword()
        setConfirmPassword()
    }

    private fun setEmail() {
        viewModelScope.launch {
            emailInput.collect { email -> _state.update { it.copy(email = email) } }
        }
    }

    private fun setFullName() {
        viewModelScope.launch {
            fullNameInput.collect { fullName ->

                _state.update { it.copy(fullName = fullName) }
            }
        }
    }

    private fun setPassword() {
        viewModelScope.launch {
            passwordInput.collect { password -> _state.update { it.copy(password = password) } }
        }
    }

    private fun setConfirmPassword() {
        viewModelScope.launch {
            confirmPasswordInput.collect { confirmPassword ->
                _state.update {
                    it.copy(
                        confirmPassword = confirmPassword
                    )
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
        _state.update { it.copy(isLoading = false, validationState = result) }

        Log.e("TAG", "addUser in viewModel : $result")
    }

    private fun onError(exception: Exception) {
        handleException(exception)
        Log.e("TAG", "Throwable error: ${exception.message}")
    }


    private fun handleException(exception: Exception) {
        when (exception) {
            is InvalidEmailException -> {
                _state.update { it.copy(isLoading = false, isSignUp = false, isError = true) }
                Log.e("TAG", "InvalidEmailException error: ${exception.message}")
            }

            is InvalidFullNameException -> {
                _state.update { it.copy(isLoading = false, isSignUp = false, isError = true) }
                Log.e("TAG", "InvalidFullNameException error: ${exception.message}")
            }

            is InvalidPasswordException -> {
                _state.update { it.copy(isLoading = false, isSignUp = false, isError = true) }
                Log.e("TAG", "InvalidPasswordException error: ${exception.message}")
            }

            else -> {
                _state.update { it.copy(isLoading = false, isSignUp = false, isError = true) }
                Log.e("TAG", "Throwable error: ${exception.message}")
            }
        }
    }

    fun onContinueClicked() {
        val validationState = validateFullNameAndEmail(fullNameInput.value, emailInput.value)
        if (validationState == ValidationState.SUCCESS) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
            Log.e("TAG", "on click  continue SUCCESS:$validationState ")
        }
        _state.update { it.copy(validationState = validationState) }

        Log.e("TAG", "on click  continue validationState:$validationState ")
        Log.e("TAG", "on click  continue handleValidation:${handleValidation(validationState)} ")
    }

    fun onSignupClicked() {
        val validationState =
            validateConfirmPassword(passwordInput.value, confirmPasswordInput.value)
        if (validationState == ValidationState.VALID_PASSWORD) {
            addUser(
                fullName = _state.value.fullName,
                password = _state.value.password,
                email = _state.value.email
            )
        }
        _state.update { it.copy(validationState = validationState) }
        Log.e("TAG", "on click  signup: ")
    }


}