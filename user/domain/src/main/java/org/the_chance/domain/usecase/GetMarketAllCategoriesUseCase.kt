package org.the_chance.domain.usecase

import org.the_chance.data.repository.HoneyMartRepository
import org.the_chance.domain.model.Category
import org.the_chance.domain.model.asCategory

class GetMarketAllCategoriesUseCase @Inject constructor(
    private val honeyMarketRepository : HoneyMartRepository
){
     suspend operator fun invoke(id: Long): List<Category> {
         return honeyMarketRepository.getAllCategories(id).categories.map { it.asCategory() }
     }
}