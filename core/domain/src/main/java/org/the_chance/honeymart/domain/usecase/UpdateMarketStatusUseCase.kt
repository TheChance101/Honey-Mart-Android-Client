package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateMarketStatusUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(status: Int): Boolean {
        return honeyMartRepository.updateMarketStatus(status)
    }
}