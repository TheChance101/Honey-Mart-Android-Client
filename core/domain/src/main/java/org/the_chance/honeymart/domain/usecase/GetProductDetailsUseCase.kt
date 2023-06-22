package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {

    suspend operator fun invoke(productId: Long, categoryId: Long): ProductEntity =
        filterProductById(
            honeyMartRepository.getAllProductsByCategory(categoryId) ?: emptyList(),
            productId
        )

    private fun filterProductById(
        products: List<ProductEntity>,
        productId: Long
    ): ProductEntity = products.find { it.productId == productId }!!
}