package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidPasswordInputException
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationUseCase: IValidationUseCase
) {
    suspend operator fun invoke(email: String, password: String) {
        when (validationUseCase.validateEmail(email)) {
            ValidationState.BLANK_EMAIL, ValidationState.INVALID_EMAIL -> throw InvalidEmailException()
            else -> {}
        }

        when (validationUseCase.validationPassword(password)) {
            ValidationState.BLANK_PASSWORD, ValidationState.INVALID_PASSWORD,
            ValidationState.INVALID_PASSWORD_LENGTH_SHORT -> throw InvalidPasswordInputException()
            else -> {}
        }

        val deviceToken = authRepository.getDeviceToken()
        try {
            val tokens = authRepository.loginUser(email, password, deviceToken)
            authRepository.saveTokens(tokens.accessToken, tokens.refreshToken)
        } catch (e: Exception) {
            throw InvalidPasswordInputException()
        }
    }
}