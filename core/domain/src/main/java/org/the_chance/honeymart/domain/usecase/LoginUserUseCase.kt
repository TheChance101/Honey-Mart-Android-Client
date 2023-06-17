package org.the_chance.honeymart.domain.usecase

import kotlinx.coroutines.runBlocking
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {
    suspend operator fun invoke(email: String, password: String): ValidationState {
        val emailValidationState = validateEmailUseCase(email)
        val passwordValidationState = validatePasswordUseCase(password)
        return if (emailValidationState != ValidationState.VALID_EMAIL
        ) {
            emailValidationState
        } else if (passwordValidationState != ValidationState.VALID_PASSWORD) {
            passwordValidationState
        } else {
            val token = authRepository.loginUser(email, password)
            println("Token is: $token")
            runBlocking {
                authRepository.saveToken(token)
            }

            ValidationState.SUCCESS
        }
    }

}