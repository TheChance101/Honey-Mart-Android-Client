package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.model.asMarket
import javax.inject.Inject


class GetAllMarketUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): List<Market> {
        return honeyMartRepository.getAllMarket()?.map { it.asMarket() } ?: emptyList()
    }

}