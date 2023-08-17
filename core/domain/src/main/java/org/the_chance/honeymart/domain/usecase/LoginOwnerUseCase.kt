package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginOwnerUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationLoginFieldsUseCase: ValidationLoginFieldsUseCase,
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        val emailValidationState = validationLoginFieldsUseCase.validateEmail(email)
        val passwordValidationState = validationLoginFieldsUseCase.validatePassword(password)

        return if (emailValidationState != ValidationState.VALID_EMAIL) {
            false
        } else if (passwordValidationState != ValidationState.VALID_PASSWORD) {
            false
        } else {
            val response = authRepository.loginOwner(email, password)
            authRepository.saveToken(response.tokens.accessToken)
            authRepository.saveOwnerName(response.fullName)
            authRepository.saveOwnerImageUrl("")
            true
        }
    }
}