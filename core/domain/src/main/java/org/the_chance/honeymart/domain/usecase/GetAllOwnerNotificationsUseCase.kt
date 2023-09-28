package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllOwnerNotificationsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(notificationState: Int): List<Notification> {
        val notificationList = honeyMartRepository.getAllOwnerNotifications()

        return when (notificationState) {
            1 -> notificationList
            2 -> notificationList.filter { it.title.contains( "New Order received!" )}
            else -> notificationList.filterNot { it.title.contains( "New Order received!" ) }
        }
    }
}
