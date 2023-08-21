package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.UserLoginEntity
import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): UserLoginEntity {
        val refreshToken = authRepository.getRefreshToken()

        val tokens = authRepository.refreshToken(refreshToken!!)
        authRepository.saveTokens(tokens.accessToken, tokens.refreshToken)
        return tokens
    }

}