package org.the_chance.honeymart.ui.feature.profile

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.usecase.AddProfileImageUseCase
import org.the_chance.honeymart.domain.usecase.GetProfileUserUseCase
import org.the_chance.honeymart.domain.usecase.LogoutUserUseCase
import org.the_chance.honeymart.domain.usecase.SaveThemeUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val addProfileImageUseCase: AddProfileImageUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProfileUiState, ProfileUiEffect>(ProfileUiState()),
    ProfileInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()


    init {
        getData()
    }

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

    private fun onGetProfileSuccess(user: ProfileUserEntity) {
        _state.update { it.copy(isLoading = false, accountInfo = user.toProfileUiState()) }
    }

    private fun onGetProfileError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, isError = true ,error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
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

    override fun onClickTheme() {
        _state.update { it.copy(isDark = ! it.isDark) }
        effectActionExecutor(_effect, ProfileUiEffect.ClickThemeEffect)
    }

     fun onClickThemeState(isDark :Boolean) {
        tryToExecute(
            function = { saveThemeUseCase(isDark) },
            onSuccess = { ::onChangeThemeSuccess },
            onError = {}
        )
    }

    private fun onChangeThemeSuccess(isDark: Boolean) {
//        _state.update { it.copy(isDark  = ! it.isDark) }
    }

    override fun showSnackBar(massage: String) {
        TODO("Not yet implemented")
    }

    override fun showDialog() {
        _state.update {
            it.copy(isShowDialog = true)
        }
    }

    override fun resetDialogState() {
        _state.update { it.copy(isShowDialog = false) }
    }

    override fun onImageSelected(image: ByteArray) {
        _state.update { it.copy(image = image) }
    }

    override fun updateImage(userId: Long, image: ByteArray) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProfileImageUseCase(userId, image) },
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


    override fun onClickLogout() {
        tryToExecute(
            function = { logoutUserUseCase() },
            onSuccess = { onLogoutSuccess() },
            onError = ::onLogoutError
        )
        resetDialogState()
    }

    private fun onLogoutSuccess() {
        effectActionExecutor(_effect, ProfileUiEffect.ClickLogoutEffect)
    }

    private fun onLogoutError(error: ErrorHandler) {
    }
}