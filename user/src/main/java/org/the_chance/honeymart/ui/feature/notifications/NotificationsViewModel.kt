package org.the_chance.honeymart.ui.feature.notifications

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.usecase.GetAllNotificationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotifications: GetAllNotificationsUseCase,
) : BaseViewModel<NotificationsUiState, NotificationsUiEffect>(NotificationsUiState()),
    NotificationsInteractionListener {

    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllNotifications()
    }

    override fun getAllNotifications() {
        _state.update { it.copy(isLoading = !it.isRefresh) }
        tryToExecute(
            { getNotifications() },
            ::onGetAllNotificationsSuccess,
            ::onError
        )
    }

    override fun onGetAllNotifications() {
        _state.update {
            it.copy(
                notificationState = NotificationStates.ALL,
                updatedNotifications = it.notifications,
            )
        }
    }

    override fun onGetProcessingNotifications() {
        _state.update {
            it.copy(notificationState = NotificationStates.PROCESSING,
                updatedNotifications = it.notifications.filter { it.title == "Order in progress!" },
            )
        }
    }

    override fun onGetCompletedNotifications() {
        _state.update {
            it.copy(
                notificationState = NotificationStates.COMPLETED,
                updatedNotifications = it.notifications.filter { it.title != "Order in progress!" },
            )
        }
    }

    override fun onClickTryAgain() {
        getAllNotifications()
    }

    private fun onGetAllNotificationsSuccess(notifications: List<Notification>) {
        _state.update { notificationsUiState ->
            notificationsUiState.copy(
                isLoading = false,
                isRefresh = false,
                isError = false,
                error = null,
                notifications = notifications.map { it.toNotificationUiState() },
                updatedNotifications = notifications.map { it.toNotificationUiState() },
            )
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickDiscoverMarket() {
        effectActionExecutor(_effect, NotificationsUiEffect.OnClickDiscoverMarket)
    }

    override fun onRefresh() {
        _state.update { it.copy(isRefresh = true, isError = false, error = null) }
        getAllNotifications()
    }

}