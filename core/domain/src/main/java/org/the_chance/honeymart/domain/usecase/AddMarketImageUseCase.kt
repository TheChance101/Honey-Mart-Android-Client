package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddMarketImageUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
//    suspend operator fun invoke(
//        marketImage: ByteArray
//    ): Boolean {
//        return honeyMartRepository.addMarketImage(marketImage)
//    }
}