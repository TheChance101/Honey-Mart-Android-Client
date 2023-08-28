package org.the_chance.honeymart.ui.feature.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.UserProfile
import org.the_chance.honeymart.domain.usecase.AddProfileImageUseCase
import org.the_chance.honeymart.domain.usecase.GetProfileUserUseCase
import org.the_chance.honeymart.domain.usecase.LogoutUserUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val addProfileImageUseCase: AddProfileImageUseCase,
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()), ProfileInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    init { getData() }

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, isError = false)
        }
        tryToExecute(
            { getProfileUseCase() },
            ::onGetProfileSuccess,
            ::onGetProfileError
        )
    }

    override fun onClickLogin() {
        effectActionExecutor(_effect, ProfileUiEffect.UnAuthorizedUserEffect)
    }

    private fun onGetProfileSuccess(user: UserProfile) {
        _state.update { it.copy(isLoading = false, accountInfo = user.toProfileUiState(), error = null) }
    }

    private fun onGetProfileError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = true,
                isConnectionError = error is ErrorHandler.NoConnection,
            )
        }
    }

    override fun onClickMyOrder() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickMyOrderEffect)
    }

    override fun onClickCoupons() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickCouponsEffect)
    }

    override fun onClickNotification() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickNotificationEffect)
    }


    override fun resetDialogState() {
        _state.update { it.copy(isShowDialog = false) }
    }

    override fun onImageSelected(image: ByteArray) {
        _state.update { it.copy(image = image) }
    }

    override fun updateImage(image: ByteArray) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProfileImageUseCase(image) },
            onSuccess = { onAddProfileImagesSuccess() },
            onError = ::onAddProfileImagesError
        )
    }

    private fun onAddProfileImagesSuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
        getData()
    }

    private fun onAddProfileImagesError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }


    override fun showDialog() {
        _state.update {
            it.copy(isShowDialog = true)
        }
    }

    override fun onClickLogout() {
        tryToExecute(
            function = { logoutUserUseCase() },
            onSuccess = { onLogoutSuccess() },
            onError = {onLogoutError()}
        )
    }
    private fun onLogoutSuccess() {
        resetDialogState()
        effectActionExecutor(_effect, ProfileUiEffect.ClickLogoutEffect)
    }
    private fun onLogoutError() {}
}