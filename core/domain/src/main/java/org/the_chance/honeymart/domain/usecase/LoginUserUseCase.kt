package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) {
        val deviceToken = authRepository.getDeviceToken()
        val tokens = authRepository.loginUser(email, password, deviceToken)
        authRepository.saveTokens(tokens.accessToken, tokens.refreshToken)
    }
}