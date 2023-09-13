package org.the_chance.honeymart.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOwnerInfoUseCase
import org.the_chance.honeymart.domain.usecase.LogOutOwnerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOwnerProfileInfo: GetOwnerInfoUseCase,
    private val logOutOwnerUseCase: LogOutOwnerUseCase,
) : BaseViewModel<MainUiState, MainEffect>(MainUiState()),
    MainInteractionListener {

    override val TAG: String
        get() = this::class.simpleName.toString()

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
        log(error.toString())
        effectActionExecutor(_effect, MainEffect.ShowLogoutErrorToastEffect)
    }

    override fun onGetOwnerInitials() {
        tryToExecute(
            { getOwnerProfileInfo.getOwnerNameFirstCharacter() },
            ::onGetOwnerInitialsSuccess,
            ::onGetOwnerInitialsError
        )
    }

    private fun onGetOwnerInitialsSuccess(initials: Char) {
        _state.update {
            it.copy(
                ownerNameFirstCharacter = initials.toString().uppercase(Locale.ROOT),
                ownerImageUrl = getOwnerProfileInfo.getOwnerImageUrl()
            )
        }
    }

    private fun onGetOwnerInitialsError(error: ErrorHandler) {
        log(error.toString())
    }
}