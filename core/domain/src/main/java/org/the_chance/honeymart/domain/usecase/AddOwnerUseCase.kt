package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository

class AddOwnerUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(fullName: String, email: String, password: String): Boolean {
        return authRepository.createOwnerAccount(fullName, email, password)
    }
}