package org.the_chance.honeymart.ui.feature.notifications

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.NotificationEntity
import org.the_chance.honeymart.domain.usecase.GetAllNotificationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getAllNotifications: GetAllNotificationsUseCase,
) : BaseViewModel<NotificationsUiState, NotificationsUiEffect>(NotificationsUiState()),
    NotificationsInteractionListener {
    override val TAG: String = this::class.simpleName.toString()

    override fun onClickAll() {
        _state.update {
            it.copy(isLoading = true, isError = false, notificationState = NotificationStates.ALL)
        }
        tryToExecute(
            { getAllNotifications(NotificationStates.ALL.state) },
            ::onGetAllNotificationsSuccess,
            ::onError
        )
    }

    private fun onGetAllNotificationsSuccess(notifications: List<NotificationEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                notifications = notifications.map { it.toNotificationUiState() })
        }
    }

    override fun onClickOrder() {
        _state.update {
            it.copy(isLoading = true, isError = false, notificationState = NotificationStates.ORDER)
        }
        tryToExecute(
            { getAllNotifications(NotificationStates.ORDER.state) },
            ::onGetOrderNotificationsSuccess,
            ::onError
        )
    }

    private fun onGetOrderNotificationsSuccess(notifications: List<NotificationEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                notifications = notifications.map { it.toNotificationUiState() })
        }
    }

    override fun onClickDelivery() {
        _state.update {
            it.copy(isLoading = true, isError = false, notificationState = NotificationStates.DELIVERY)
        }
        tryToExecute(
            { getAllNotifications(NotificationStates.DELIVERY.state) },
            ::onGetDeliveryNotificationsSuccess,
            ::onError
        )
    }

    private fun onGetDeliveryNotificationsSuccess(notifications: List<NotificationEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                notifications = notifications.map { it.toNotificationUiState() })
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }
}