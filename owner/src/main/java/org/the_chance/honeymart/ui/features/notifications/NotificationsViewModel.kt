package org.the_chance.honeymart.ui.features.notifications

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerNotificationsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.orders.OrderUiState
import org.the_chance.honeymart.ui.features.orders.toOrderDetailsProductUiState
import org.the_chance.honeymart.ui.features.orders.toOrderParentDetailsUiState
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val ownerNotifications: OwnerNotificationsManagerUseCase
    ):BaseViewModel<NotificationsUiState,NotificationsUiEffect>
    (NotificationsUiState()),NotificationsInteractionListener{


    override val TAG: String = this ::class.simpleName.toString()
    init {
        getAllNotifications(NotificationStates.ALL.state)
    }
    override fun getAllNotifications(notificationStates: Int){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {ownerNotifications.getNotifications(notificationStates)},
            ::onGetNotificationSuccess ,
            ::onGetNotificationsError
        )
    }
    private fun onGetNotificationSuccess(notifications : List<Notification>) {
        val notificationUiState = notifications.toNotificationUiState()
        val updateNotification = if(notifications.isEmpty())notificationUiState
        else updateSelectedOrder(notificationUiState, notificationUiState.first().notificationId)
        _state.update { notificationsUiState ->
            notificationsUiState.copy(
                isLoading = false,
                notifications = updateNotification,
                updatedNotifications = updateNotification)
        }
        getOrderDetails(_state.value.notifications.first().orderId)
    }

    private fun onGetNotificationsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getOrderDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerNotifications.getOrderDetailsUseCase(orderId) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
        getOrderProductDetails(orderId)
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetails) {
        _state.update {
            it.copy(
                isLoading = false,
                orderDetails = orderDetails.toOrderParentDetailsUiState(),
            )
        }

    }

    private fun onGetOrderDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun getOrderProductDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true ,
            notification = it.notification.copy(orderId =orderId )
        ) }
        tryToExecute(
            { ownerNotifications.getOrderProductDetailsUseCase(orderId) },
            ::onGetOrderProductDetailsSuccess,
            ::onGetOrderProductDetailsError
        )
    }

    private fun onGetOrderProductDetailsSuccess(products: List<OrderDetails.ProductDetails>) {
        _state.update {
            it.copy(
                isLoading = false,
                products = products.toOrderDetailsProductUiState(),
            )
        }
    }

    private fun onGetOrderProductDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun updateSelectedOrder(notifications: List<NotificationUiState>,
        selectedNotificationId: Long,
    ): List<NotificationUiState> {
        return notifications.map { notification ->
            notification.copy(
                isNotificationSelected = notification.notificationId == selectedNotificationId)
        }
    }

    override fun onCLickNotificationCard(orderDetails: OrderUiState, notification :NotificationUiState) {
        val updateNotification = updateSelectedOrder(_state.value.notifications,
            notification.notificationId)
        _state.update {
            val newOrderDetails = it.orderDetails.copy(orderId = notification.orderId)
            it.copy(
                notifications = updateNotification,
                updatedNotifications =updateNotification ,
                orderDetails = newOrderDetails,
            )
        }
        getOrderDetails(notification.orderId)
    }


}