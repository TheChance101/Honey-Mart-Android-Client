package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllMarketOrdersUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(orderState: Int): List<Market.Order> {
        val allOrders = honeyMartRepository.getAllMarketOrders(orderState)
        return if (orderState == 0) {
            allOrders.filterNot { it.state == 4 }
        } else {
            allOrders
        }
    }
}