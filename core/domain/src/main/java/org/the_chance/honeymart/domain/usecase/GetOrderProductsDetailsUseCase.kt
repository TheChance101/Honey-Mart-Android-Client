package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetOrderProductsDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(orderId: Long): List<OrderProductDetailsEntity> =
        honeyMartRepository.getOrderDetails(orderId).products ?: emptyList()
}