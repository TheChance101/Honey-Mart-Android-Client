package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Order
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(orderState:Int): List<Order> {
        return honeyMartRepository.getAllOrders(orderState)
    }
}