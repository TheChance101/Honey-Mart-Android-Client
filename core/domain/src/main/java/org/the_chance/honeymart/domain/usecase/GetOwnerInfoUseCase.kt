package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetOwnerInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    fun getOwnerNameFirstCharacter(): Char {
        return authRepository.getOwnerName()?.firstOrNull() ?: ' '
    }

    fun getOwnerImageUrl(): String {
        return authRepository.getOwnerImageUrl() ?: ""
    }
}