package org.the_chance.honeymart.domain.usecase.usecaseManager.user

import org.the_chance.honeymart.domain.usecase.GetAllMarketOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import javax.inject.Inject

data class UserOrderDetailsManagerUseCase @Inject constructor(
    val getAllMarketOrders: GetAllMarketOrdersUseCase,
    val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    val getOrderProductDetailsUseCase: GetOrderProductsDetailsUseCase,
    val updateOrderStateUseCase: UpdateOrderStateUseCase,
)
