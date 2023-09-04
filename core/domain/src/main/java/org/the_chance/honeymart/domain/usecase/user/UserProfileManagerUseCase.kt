package org.the_chance.honeymart.domain.usecase.user

import org.the_chance.honeymart.domain.usecase.AddProfileImageUseCase
import org.the_chance.honeymart.domain.usecase.GetProfileUserUseCase
import javax.inject.Inject

data class UserProfileManagerUseCase @Inject constructor(
    val getProfileUserUseCase: GetProfileUserUseCase,
    val addProfileImageUseCase: AddProfileImageUseCase
)
