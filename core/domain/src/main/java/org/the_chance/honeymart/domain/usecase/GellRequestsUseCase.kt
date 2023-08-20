package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetRequestsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(requestState:Int): List<RequestEntity> {
        return honeyMartRepository.getAllRequests(requestState)
    }
}