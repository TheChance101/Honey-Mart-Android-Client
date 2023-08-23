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

    init {
        getAllNotifications()
    }

    override fun getAllNotifications() {
        tryToExecute(
            { getAllNotifications(NotificationStates.ALL.state) },
            ::onGetAllNotificationsSuccess,
            ::onError
        )
    }

    override fun onGetAllNotifications() {
        _state.update {
            it.copy(notificationState = NotificationStates.ALL,
                updatedNotifications = it.notifications,
            )
        }
    }

    override fun onGetOrderNotifications() {
        _state.update {
            it.copy(notificationState = NotificationStates.ORDER,
                updatedNotifications = it.notifications.filter { it.title != "Order Is Complete!" },
            )
        }
    }

    override fun onGetDeliveryNotifications() {
        _state.update {
            it.copy(
                notificationState = NotificationStates.DELIVERY,
                updatedNotifications = it.notifications.filter { it.title == "Order Is Complete!" },
            )
        }
    }

    private fun onGetAllNotificationsSuccess(notifications: List<NotificationEntity>) {
        _state.update { notificationsUiState ->
            notificationsUiState.copy(
                notifications = notifications.map { it.toNotificationUiState() },
                updatedNotifications = notifications.map { it.toNotificationUiState() }
               )
        }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }
}