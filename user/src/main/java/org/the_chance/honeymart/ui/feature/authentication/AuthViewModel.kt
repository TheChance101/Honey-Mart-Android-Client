package org.the_chance.honeymart.ui.feature.authentication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.honeymart.ui.feature.ui_effect.AuthenticationUiEffect
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SignupUiState, AuthenticationUiEffect>(SignupUiState()) {
    override val TAG: String = this::class.java.simpleName
    private val authData = AuthFragmentArgs.fromSavedStateHandle(savedStateHandle).AuthData

    fun onClickSignUp() {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    AuthenticationUiEffect.ClickSignUpEffect(
                        authData
                    )
                )
            )
        }
    }

    fun onClickLogin() {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    AuthenticationUiEffect.ClickLoginEffect(
                        authData
                    )
                )
            )
        }
    }

}