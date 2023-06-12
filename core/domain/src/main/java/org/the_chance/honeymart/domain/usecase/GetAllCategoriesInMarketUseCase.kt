package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.CategoryEntity

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllCategoriesInMarketUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(marketId: Long): List<CategoryEntity> =
        honeyMartRepository.getCategoriesInMarket(marketId) ?: emptyList()
}
