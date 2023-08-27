package org.the_chance.honeymart.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOwnerInfoUseCase
import org.the_chance.honeymart.domain.usecase.LogOutOwnerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ownerProfileInfo: GetOwnerInfoUseCase,
    private val logOutOwnerUseCase: LogOutOwnerUseCase,
) : BaseViewModel<MainUiState, MainEffect>(MainUiState()),
    MainInteractionListener {

    override val TAG: String
        get() = this::class.simpleName.toString()

    init {
        getOwnerInfo()
    }

    private fun getOwnerInfo() {
        _state.update {
            it.copy(
                ownerNameFirstCharacter = ownerProfileInfo.getOwnerNameFirstCharacter(),
                ownerImageUrl = ownerProfileInfo.getOwnerImageUrl()
            )
        }
    }

    override fun onClickProfile() {
        effectActionExecutor(_effect, MainEffect.OnClickProfileEffect)
    }

    override fun onClickLogout() {
        tryToExecute(
            function = { logOutOwnerUseCase() },
            onSuccess = { onLogoutSuccess() },
            onError = ::onLogoutError
        )
    }

    private fun onLogoutSuccess() {
        effectActionExecutor(_effect, MainEffect.OnClickLogoutEffect)
    }

    private fun onLogoutError(error: ErrorHandler) {

    }
}