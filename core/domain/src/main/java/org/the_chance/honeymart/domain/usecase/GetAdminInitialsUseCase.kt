package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetAdminInitialsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Char {
        return authRepository.getAdminName()?.firstOrNull() ?: ' '
    }
}