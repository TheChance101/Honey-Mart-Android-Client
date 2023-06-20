package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(orderId: Long): OrderDetailsEntity =
        honeyMartRepository.getOrderDetails(orderId)
}