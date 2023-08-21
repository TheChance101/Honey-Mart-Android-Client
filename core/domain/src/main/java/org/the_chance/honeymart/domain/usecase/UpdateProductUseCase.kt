package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        price: Double,
        description: String
    ): String {
        return honeyMartRepository.updateProduct(id, name, price, description)
    }
}