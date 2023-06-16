package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): String =
        authRepository.loginUser(email, password)

}