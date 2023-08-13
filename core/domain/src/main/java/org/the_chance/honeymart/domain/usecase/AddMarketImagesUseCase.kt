package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddMarketImagesUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        marketId: Long,
        marketImages: List<ByteArray>
    ): String {
        return honeyMartRepository.addMarketImages(marketId, marketImages)
    }
}