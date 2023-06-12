package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetCategoriesForSpecificProductUseCase @Inject constructor(
    private val honeyMarketRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long): List<CategoryEntity> =
        honeyMarketRepository.getCategoriesForSpecificProduct(productId) ?: emptyList()
}