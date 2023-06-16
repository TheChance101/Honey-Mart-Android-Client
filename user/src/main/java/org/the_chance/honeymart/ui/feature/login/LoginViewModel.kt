package org.the_chance.honeymart.ui.feature.login

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) :
    BaseViewModel<LoginUiState, Long>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName
    init {
       // handleValidation()
    }

}