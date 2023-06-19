package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {

    suspend operator fun invoke(productId: Long, categoryId: Long): List<ProductEntity> =
        filterProductById(
            honeyMartRepository.getAllProductsByCategory(categoryId) ?: emptyList(),
            productId
        )

    private fun filterProductById(
        products: List<ProductEntity>,
        productId: Long
    ): List<ProductEntity> {
        return products.filter { it.productId == productId }.take(1)
    }
}