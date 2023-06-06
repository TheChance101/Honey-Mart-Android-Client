package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.asCategory
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(marketId: Long): List<Category> {
        return honeyMartRepository.getAllCategories(marketId).map { it.asCategory() }
    }
}