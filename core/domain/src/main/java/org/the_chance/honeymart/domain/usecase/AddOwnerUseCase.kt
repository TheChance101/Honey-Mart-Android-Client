package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class AddOwnerUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(fullName: String, email: String, password: String): Boolean {
        return authRepository.createOwnerAccount(fullName, email, password)
    }
}