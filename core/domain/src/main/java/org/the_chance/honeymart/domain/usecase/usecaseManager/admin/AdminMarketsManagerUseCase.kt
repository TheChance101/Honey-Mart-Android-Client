package org.the_chance.honeymart.domain.usecase.usecaseManager.admin

import org.the_chance.honeymart.domain.usecase.GetMarketsRequests
import org.the_chance.honeymart.domain.usecase.UpdateMarketRequestUseCase

data class AdminMarketsManagerUseCase(
    val getMarkets: GetMarketsRequests,
    val updateMarket: UpdateMarketRequestUseCase
)
