package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketDetailsEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetMarketDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(marketId: Long): MarketDetailsEntity =
        honeyMartRepository.getMarketDetails(marketId)
}