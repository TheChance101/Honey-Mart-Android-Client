package org.the_chance.honeymart.domain.usecase.usecaseManager.owner

import org.the_chance.honeymart.domain.usecase.GetAllOwnerNotificationsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import javax.inject.Inject

data class OwnerNotificationsManagerUseCase @Inject constructor(
     val getNotifications: GetAllOwnerNotificationsUseCase,
    val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    val getOrderProductDetailsUseCase: GetOrderProductsDetailsUseCase,
)
