package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LoginAdminUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String) {
        val admin = authRepository.loginAdmin(email, password)
        authRepository.saveTokens(admin.tokens.accessToken, admin.tokens.refreshToken)
        authRepository.saveAdminName(admin.name)
    }
}