package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.ValidationState
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateFullNameUseCase: ValidateFullNameUseCase,
) {
    suspend operator fun invoke(
        fullName: String,
        password: String,
        email: String,
    ): ValidationState {
        val emailValidationState = validateEmailUseCase(email)
        val passwordValidationState = validatePasswordUseCase(password)
        val fullNameValidationState = validateFullNameUseCase(fullName)

        return if (emailValidationState != ValidationState.VALID_EMAIL) {
            emailValidationState
        } else if (passwordValidationState != ValidationState.VALID_PASSWORD) {
            passwordValidationState
        } else if (fullNameValidationState != ValidationState.VALID_FULL_NAME) {
            fullNameValidationState
        } else {
            authRepository.addUser(fullName, password, email)
            ValidationState.SUCCESS
        }

    }


}
