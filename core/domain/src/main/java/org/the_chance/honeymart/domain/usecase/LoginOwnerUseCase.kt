package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LoginOwnerUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
//    suspend operator fun invoke(email: String, password: String) {
//        val token = authRepository.loginOwner(email, password)
//        authRepository.saveToken(token.tokens.accessToken)
//        authRepository.saveOwnerName(token.fullName)
//    }
}
