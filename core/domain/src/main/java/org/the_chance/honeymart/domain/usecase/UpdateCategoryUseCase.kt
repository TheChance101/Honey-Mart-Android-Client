package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        id: Long,
        name: String,
        marketId: Long,
        imageId: Int
    ): String {
        return honeyMartRepository.updateCategory(id, name, marketId, imageId)
    }
}