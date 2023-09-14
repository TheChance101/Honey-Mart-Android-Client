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
            0 -> notificationList // Return all notifications
            1 -> notificationList.filter { it.title == "New Order received!" }
            else -> notificationList.filterNot { it.title == "New Order received!" }
        }
    }
}
