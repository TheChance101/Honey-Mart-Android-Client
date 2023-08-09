package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddProductWithImagesUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        name: String,
        price: Double,
        description: String,
        categoriesId: Long,
        images: List<ByteArray>
    ): ProductEntity {
        val product = honeyMartRepository.addProduct(name, price, description, categoriesId)
        honeyMartRepository.addImageProduct(product.productId, images)
        return product
    }
}