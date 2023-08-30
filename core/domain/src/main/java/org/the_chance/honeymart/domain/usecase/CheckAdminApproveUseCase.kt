package org.the_chance.honeymart.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class CheckAdminApproveUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke() : Flow<Boolean> {
        return honeyMartRepository.checkAdminApprove()
    }
}