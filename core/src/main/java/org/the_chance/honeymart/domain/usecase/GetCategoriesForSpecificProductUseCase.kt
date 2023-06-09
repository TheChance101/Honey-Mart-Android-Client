package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.asCategory
import javax.inject.Inject

class GetCategoriesForSpecificProductUseCase @Inject constructor(
    private val honeyMarketRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long): List<Category> =
        honeyMarketRepository.getCategoriesForSpecificProduct(productId)?.map { it.asCategory() }
            ?: emptyList()
}