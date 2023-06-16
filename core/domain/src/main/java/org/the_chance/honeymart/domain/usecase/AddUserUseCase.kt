package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(
        fullName: String,
        password: String,
        email: String,
    ): Boolean? =
        honeyMartRepository.addUser(fullName, password, email)

    init {
        println("AddUserUseCase init")
    }
}
