package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetMarketsRequests @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        isApproved: Boolean? = null,
        marketState: Int,
    ): List<MarketRequest> {
        return when (marketState) {
            1 -> honeyMartRepository.getMarketsRequests(isApproved)
            2 -> honeyMartRepository.getMarketsRequests(isApproved).filter { it.isApproved  }
            else ->honeyMartRepository.getMarketsRequests(isApproved).filter { !it.isApproved  }

        }
    }
}