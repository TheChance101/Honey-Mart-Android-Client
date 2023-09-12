package org.the_chance.honeymart.ui.features.notifications

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.usecase.GetAllOwnerNotificationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotifications: GetAllOwnerNotificationsUseCase,
    ):BaseViewModel<NotificationsUiState,NotificationsUiEffect>
    (NotificationsUiState()),NotificationsInteractionListener{

    override val TAG: String = this ::class.simpleName.toString()
    init {

    }
    override fun getAllNotifications(){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {getNotifications()},
            ::onGetNotificationSuccess ,
            ::onGetNotificationsError
        )


    }
    private fun onGetNotificationSuccess(notifications : List<Notification>){

    }
    private fun onGetNotificationsError(error : ErrorHandler){

    }

}