package org.the_chance.honeymart.ui.feature.login

import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState

class LoginViewModel : BaseViewModel<LoginUiState, Long>(LoginUiState()) {
    override val TAG: String = this::class.java.simpleName

}