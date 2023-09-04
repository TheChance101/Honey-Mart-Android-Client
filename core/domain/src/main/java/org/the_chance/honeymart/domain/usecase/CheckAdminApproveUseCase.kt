package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class CheckAdminApproveUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
    private val authenticationUseCase: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val marketApproval = honeyMartRepository.checkAdminApprove()
        authenticationUseCase.saveOwnerMarketId(marketApproval.marketId)
        return marketApproval.isMarketApproved
    }
}