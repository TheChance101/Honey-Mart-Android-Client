package org.the_chance.honeymart.ui.feature.authentication

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.signup.SignupUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() :
    BaseViewModel<SignupUiState, AuthenticationUiEffect>(SignupUiState()), AuthenticationInteractionListener {
    override val TAG: String = this::class.java.simpleName
   override fun onClickSignUp() {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    AuthenticationUiEffect.ClickSignUpEffect
                )
            )
        }
    }

   override fun onClickLogin() {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    AuthenticationUiEffect.ClickLoginEffect
                )
            )
        }
    }
}