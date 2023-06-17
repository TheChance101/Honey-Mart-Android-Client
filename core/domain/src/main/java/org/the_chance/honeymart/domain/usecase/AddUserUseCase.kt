package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        fullName: String,
        password: String,
        email: String,
    ): Boolean =
        authRepository.addUser(fullName, password, email) ?: false


}
