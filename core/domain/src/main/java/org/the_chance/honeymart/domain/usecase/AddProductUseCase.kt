package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        name: String,
        price: Double,
        description: String,
        categoryId: Long
    ): Product {
        return honeyMartRepository.addProduct(name, price, description, categoryId)
    }
}