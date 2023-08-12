package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    fun getProfileName(): String {
        return authRepository.getProfileName()?:""
    }

    fun getProfileImage(): String {
        return authRepository.getProfileImageUrl()?:""
    }
}