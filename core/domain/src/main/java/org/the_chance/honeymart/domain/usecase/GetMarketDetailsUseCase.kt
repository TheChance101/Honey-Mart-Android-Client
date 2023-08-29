package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketDetails
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetMarketDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(marketId: Long): MarketDetails =
        honeyMartRepository.getMarketDetails(marketId)
}