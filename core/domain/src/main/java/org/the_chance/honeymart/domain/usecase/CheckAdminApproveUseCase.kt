package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class CheckAdminApproveUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
    private val authenticationUseCase: AuthRepository
) {
    suspend operator fun invoke(): Long {
        val marketId = honeyMartRepository.checkAdminApprove()
        authenticationUseCase.saveOwnerMarketId(marketId)
        return marketId
    }
}