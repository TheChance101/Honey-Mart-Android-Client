package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetMarketsRequests @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(isApproved:Boolean? = null): List<MarketRequest> {
        return honeyMartRepository.getMarketsRequests(isApproved)
    }
}