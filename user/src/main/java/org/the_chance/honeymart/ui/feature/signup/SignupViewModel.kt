package org.the_chance.honeymart.ui.feature.signup

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddUserUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createUser: AddUserUseCase,
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
            fullNameInput.collect { fullName -> _state.update { it.copy(fullName = fullName) } }
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

    private fun addUser() {
        _state.update { it.copy(isLoading = true) }
        try {
            viewModelScope.launch {
                val result =
                    createUser(_state.value.fullName, _state.value.password, _state.value.email)
                _state.update { it.copy(isLoading = false, isSignUp = result) }
                Log.e("TAG", "addUser in viewModel : $result")
            }
        } catch (t: Throwable) {
            Log.e("TAG", "addUser error: ${t.message}")
        }
    }

    fun onContinueClicked() {
        if (registerValidation(fullNameInput.value, emailInput.value) == 0) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
        }
        Log.e("TAG", "on click  continue: ")
    }

    fun onSignupClicked() {
        if (passwordValidation(passwordInput.value, confirmPasswordInput.value) == 0) {
            addUser()

        }
        if (_state.value.isSignUp) {
            viewModelScope.launch { _effect.emit(EventHandler(true)) }
        }
        Log.e("TAG", "on click  signup: ")
    }


    fun registerValidation(fullName: String, email: String): Int {
        if (fullName.isBlank() && email.isBlank()) {
            _state.update { it.copy(error = listOf("Please fill all the fields")) }
            return 1
        } else if (fullName.length < 3) {
            _state.update { it.copy(error = listOf("Full name must be at least 3 characters")) }
            return 2
        } else if (email.length < 3) {
            _state.update { it.copy(error = listOf("Email must be at least 3 characters")) }
            return 3
        }
        return 0
    }

    fun passwordValidation(password: String, confirmPassword: String): Int {
        if (password.isBlank() && confirmPassword.isBlank()) {
            _state.update { it.copy(error = listOf("Please fill all the fields")) }
            return 1
        } else if (password.length < 6) {
            _state.update { it.copy(error = listOf("Password must be at least 6 characters")) }
            return 2
        } else if (password != confirmPassword) {
            _state.update { it.copy(error = listOf("Password and confirm password must be the same")) }
            return 3
        }
        return 0
    }


}