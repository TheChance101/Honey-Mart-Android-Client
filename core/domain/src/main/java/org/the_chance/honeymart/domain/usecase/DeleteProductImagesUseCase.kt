package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class DeleteProductImagesUseCase @Inject constructor(
    val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId :Long):String{
        return honeyMartRepository.deleteProductImage(productId)

    }
}