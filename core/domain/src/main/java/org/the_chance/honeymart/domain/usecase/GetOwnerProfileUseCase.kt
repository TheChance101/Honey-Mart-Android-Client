package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.OwnerProfile
import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetOwnerProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke():OwnerProfile {
        return authRepository.getOwnerProfile()
    }
}