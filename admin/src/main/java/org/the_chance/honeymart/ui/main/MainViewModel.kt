package org.the_chance.honeymart.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAdminInitialsUseCase
import org.the_chance.honeymart.domain.usecase.LogOutAdminUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAdminInitials: GetAdminInitialsUseCase,
    private val logOutAdmin: LogOutAdminUseCase,
) : BaseViewModel<MainUiState, MainEffect>(MainUiState()),
    MainInteractionListener {

    override val TAG: String
        get() = this::class.simpleName.toString()

    init {
        getAdminInfo()
    }

    private fun getAdminInfo() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    adminInitials = getAdminInitials(),
                )
            }
        }
    }

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
        effectActionExecutor(_effect, MainEffect.ShowLogoutErrorToastEffect)
    }
}