package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProcessingOrdersUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(): List<OrderEntity> {
        return honeyMartRepository.getAllOrders().filter { it.state == 0 }
    }
}