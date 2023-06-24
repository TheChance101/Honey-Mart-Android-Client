package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {

    suspend operator fun invoke(productId: Long): ProductEntity {
        return honeyMartRepository.getProductDetails(productId)

    }
}