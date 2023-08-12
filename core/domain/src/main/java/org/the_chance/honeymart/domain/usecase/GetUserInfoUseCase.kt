package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    fun getUserNameFirstCharacter(): Char {
        return authRepository.getOwnerName()?.firstOrNull() ?: ' '
    }

    fun getUserImageUrl(): String {
        return authRepository.getOwnerImageUrl() ?: ""
    }
}