package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.NotificationEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllNotificationsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(notificationState: Int): List<NotificationEntity> {
        return honeyMartRepository.getAllNotifications(notificationState)
    }
}