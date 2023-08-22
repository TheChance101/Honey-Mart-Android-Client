package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateImageProductUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long, images: List<ByteArray>): String {
        return honeyMartRepository.updateImageProduct(productId, images)
    }
}