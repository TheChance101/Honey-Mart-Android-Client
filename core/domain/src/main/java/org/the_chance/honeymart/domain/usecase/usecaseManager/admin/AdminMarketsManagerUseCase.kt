package org.the_chance.honeymart.domain.usecase.usecaseManager.admin

import org.the_chance.honeymart.domain.usecase.GetMarketsRequests
import org.the_chance.honeymart.domain.usecase.UpdateMarketRequestUseCase
import javax.inject.Inject

data class AdminMarketsManagerUseCase @Inject constructor(
    val getMarkets: GetMarketsRequests,
    val updateMarket: UpdateMarketRequestUseCase
)
