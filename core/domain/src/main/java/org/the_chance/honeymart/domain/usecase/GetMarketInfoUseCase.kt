package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketInfo
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetMarketInfoUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(): MarketInfo {
        return honeyMartRepository.getMarketInfo()
    }
}