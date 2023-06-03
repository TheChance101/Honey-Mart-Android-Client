package org.the_chance.domain.usecase

import org.the_chance.data.repository.HoneyMartRepository
import org.the_chance.domain.model.Market
import org.the_chance.domain.model.asMarket
import javax.inject.Inject


class GetAllMarketUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): List<Market> {
        return honeyMartRepository.getAllMarket()?.map { it.asMarket() } ?: emptyList()
    }

}