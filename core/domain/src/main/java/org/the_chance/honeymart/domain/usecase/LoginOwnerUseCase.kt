package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LoginOwnerUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) {
        val response = authRepository.loginOwner(email, password)
        authRepository.saveTokens(response.tokens.accessToken,response.tokens.refreshToken)
        authRepository.saveOwnerName(response.fullName)
    }
}
