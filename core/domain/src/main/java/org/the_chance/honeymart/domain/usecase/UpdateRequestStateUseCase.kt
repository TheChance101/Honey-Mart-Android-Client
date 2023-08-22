package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class UpdateRequestStateUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(id: Long?, state: Int): Boolean {
        return honeyMartRepository.updateRequestState(id, state)
    }
}