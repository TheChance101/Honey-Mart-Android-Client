package org.the_chance.honeymart.ui.feature.authentication

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.signup.SignupUiState
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor() :
    BaseViewModel<SignupUiState, AuthenticationUiEffect>(SignupUiState()),
    AuthenticationInteractionListener {
    override val TAG: String = this::class.java.simpleName
    override fun onClickSignUp() {executeAction(_effect, AuthenticationUiEffect.ClickSignUpEffect)}
    override fun onClickLogin() { executeAction(_effect, AuthenticationUiEffect.ClickLoginEffect)}
}