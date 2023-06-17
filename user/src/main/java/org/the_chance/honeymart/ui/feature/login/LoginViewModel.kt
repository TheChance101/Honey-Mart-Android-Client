package org.the_chance.honeymart.ui.feature.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
) :
    BaseViewModel<LoginUiState, Long>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName

    init {
        viewModelScope.launch {
            login("nada1111.feteiha@gmail.com", "23456789h@")
        }
    }

    private suspend fun login(email: String, password: String) {
        log(loginUserUseCase(email, password).name)
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        _state.update {
            it.copy(
                isLoading = false,
                error = 1,
            )
        }
    }

    private fun onLoginError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, error = 1) }
    }

}