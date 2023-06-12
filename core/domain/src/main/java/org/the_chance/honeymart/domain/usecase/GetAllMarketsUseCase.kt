package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllMarketsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): List<MarketEntity> {
        return honeyMartRepository.getAllMarkets() ?: emptyList()
    }

}