package org.the_chance.honeymart.ui.feature.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
) : BaseViewModel<LoginUiState, Long>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName

    val emailFlow = MutableStateFlow("")

    val passwordFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            emailFlow.collect {
                setEmail(it)
            }
        }
        viewModelScope.launch {
            passwordFlow.collect {
                setPassword(it)
            }
        }
    }

    private suspend fun login(email: String, password: String) {

        viewModelScope.launch() {
            try {
                val result = loginUserUseCase(email, password)
                Log.e(TAG, "tryToExecute:$result ")
                onLoginSuccess(result)
            } catch (e: Throwable) {
                Log.e(TAG, "tryToExecute error: ${e.message}")
                onLoginError(e)
            }
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            login(_state.value.email, _state.value.password)
        }
    }

    private fun setEmail(email: String) {
        _state.update {
            it.copy(email = email)
        }
    }

    private fun setPassword(password: String) {
        _state.update {
            it.copy(password = password)
        }
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        _state.update {
            it.copy(
                isLoading = false,
                error = 1,
                validationState = validationState
            )
        }
    }

    private fun onLoginError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, error = 1) }
    }

}