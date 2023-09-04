package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

data class OwnerOrdersManagerUseCase @Inject constructor(
    val getAllMarketOrders: GetAllMarketOrdersUseCase,
    val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    val getOrderProductDetailsUseCase: GetOrderProductsDetailsUseCase,
    val updateOrderStateUseCase: UpdateOrderStateUseCase,
)
