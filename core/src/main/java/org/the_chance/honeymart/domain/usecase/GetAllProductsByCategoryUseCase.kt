package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.asProduct
import javax.inject.Inject

class GetAllProductsByCategoryUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(categoryId: Long): List<Product> =
        honeyMartRepository.getAllProductsByCategory(categoryId)?.map { it.asProduct() }
            ?: emptyList()
}
