package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.clearToken()
    }
}