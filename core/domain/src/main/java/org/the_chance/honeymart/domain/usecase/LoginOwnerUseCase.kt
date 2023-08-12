package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginOwnerUseCase @Inject constructor(
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
            val token = authRepository.loginOwner(email, password)
            authRepository.saveToken(token)
            authRepository.saveUserName("Nour")
            authRepository.saveUserImageUrl("")
            ValidationState.SUCCESS
        }
    }
}