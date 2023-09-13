package org.the_chance.honeymart.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAdminInitialsUseCase
import org.the_chance.honeymart.domain.usecase.LogOutAdminUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAdminInitials: GetAdminInitialsUseCase,
    private val logOutAdmin: LogOutAdminUseCase,
) : BaseViewModel<MainUiState, MainEffect>(MainUiState()),
    MainInteractionListener {

    override val TAG: String
        get() = this::class.simpleName.toString()

    override fun onClickLogout() {
        tryToExecute(
            function = { logOutAdmin() },
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

    override fun onGetAdminInitials() {
        tryToExecute(
            { getAdminInitials() },
            ::onGetAdminInitialsSuccess,
            ::onGetAdminInitialsError
        )
    }

    private fun onGetAdminInitialsSuccess(initials: Char) {
        _state.update { it.copy(adminInitials = initials.toString().uppercase(Locale.ROOT)) }
    }

    private fun onGetAdminInitialsError(error: ErrorHandler) {
        log(error.toString())
    }
}