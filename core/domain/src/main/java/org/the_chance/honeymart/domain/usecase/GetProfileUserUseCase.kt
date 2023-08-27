package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProfileUserUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke():ProfileUserEntity =
        honeyMartRepository.getProfileUser()
}
