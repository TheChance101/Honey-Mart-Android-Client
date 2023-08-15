package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginOwnerUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationLoginFieldsUseCase: ValidationLoginFieldsUseCase,
) {
    suspend operator fun invoke(email: String, password: String): ValidationState {
        val emailValidationState = validationLoginFieldsUseCase.validateEmail(email)
        val passwordValidationState = validationLoginFieldsUseCase.validatePassword(password)

        return if (emailValidationState != ValidationState.VALID_EMAIL) {
            emailValidationState
        } else if (passwordValidationState != ValidationState.VALID_PASSWORD) {
            passwordValidationState
        } else {
            val token = authRepository.loginOwner(email, password)
            authRepository.saveToken(token)
            authRepository.saveOwnerName("")
            authRepository.saveOwnerImageUrl("")
            ValidationState.SUCCESS
        }
    }
}