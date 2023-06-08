package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.asProduct
import javax.inject.Inject

class GetAllCategoryProductsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(id: Long): List<Product> {
        return honeyMartRepository.getAllProductsByCategoryId(id)?.map {
            it.asProduct()
        } ?: emptyList()

    }
}