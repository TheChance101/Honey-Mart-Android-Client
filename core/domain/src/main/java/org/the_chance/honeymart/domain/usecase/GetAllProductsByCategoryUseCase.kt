package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllProductsByCategoryUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(categoryId: Long): List<ProductEntity>
    =
//        honeyMartRepository.getAllProductsByCategory(categoryId) ?: emptyList()
        listOf(
            ProductEntity(
                productId = 1, productName = "Sara", productDescription = "lalalalal",
                ProductPrice = 220.0,
                productImages = emptyList(),
            ),

            ProductEntity(
                productId = 2, productName = "Sara Salah", productDescription = "lalalalal",
                ProductPrice = 220.0,
                productImages = emptyList(),
            ),
            ProductEntity(
                productId = 3, productName = "Sara Salah Ahmed", productDescription = "lalalalal",
                ProductPrice = 220.0,
                productImages = emptyList(),
            ),

        )
}
