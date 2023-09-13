package org.the_chance.honeymart.ui.features.notifications

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.usecase.GetAllOwnerNotificationsUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerNotificationsManagerUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerOrdersManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.orders.toOrderParentDetailsUiState
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val ownerNotifications: OwnerNotificationsManagerUseCase
    ):BaseViewModel<NotificationsUiState,NotificationsUiEffect>
    (NotificationsUiState()),NotificationsInteractionListener{

    override val TAG: String = this ::class.simpleName.toString()
    init {
        getAllNotifications()

    }
    override fun getAllNotifications(){
        val hh= runBlocking { ownerNotifications.getNotifications().toString() }
        Log.e("sara",hh)
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {ownerNotifications.getNotifications()},
            ::onGetNotificationSuccess ,
            ::onGetNotificationsError
        )
    }
    private fun onGetNotificationSuccess(notifications : List<Notification>){
        log("$notifications")
        _state.update { notificationsUiState ->
            notificationsUiState.copy(
                isLoading = false,
                notifications = notifications.map { it.toNotificationUiState() },
                updatedNotifications = notifications.map { it.toNotificationUiState() },
            )
        }
    }
    private fun onGetNotificationsError(error : ErrorHandler){
        _state.update { it.copy(isLoading = false, error = error) }
        log("error $error")
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }

    }


}