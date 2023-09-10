package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateMarketRequestUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(id: Long?, isApproved: Boolean): Boolean {
        return honeyMartRepository.updateMarketRequest(id, isApproved)
    }
}