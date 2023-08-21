package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationUseCase: ValidationUseCase,

) {
    suspend operator fun invoke(email: String, password: String): ValidationState {
        val emailValidationState = validationUseCase.validateEmail(email)
        val passwordValidationState = validationUseCase.validationPassword(password)
        return if (emailValidationState != ValidationState.VALID_EMAIL
        ) {
            emailValidationState
        } else if (passwordValidationState != ValidationState.VALID_PASSWORD) {
            passwordValidationState
        } else {
            val tokens = authRepository.loginUser(email, password)
            authRepository.saveTokens(tokens.accessToken,tokens.refreshToken)
            ValidationState.SUCCESS
        }
    }

}