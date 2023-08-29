package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class CreateMarketUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ): Boolean {
        return honeyMartRepository.addMarket(
            marketName,
            marketAddress,
            marketDescription,
        )
    }
}