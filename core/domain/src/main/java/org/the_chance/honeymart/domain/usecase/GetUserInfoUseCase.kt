package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): String {
        return authRepository.getProfileImageUrl() ?: authRepository.getProfileName() ?: ""
    }

}