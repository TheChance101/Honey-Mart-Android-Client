package org.the_chance.honeymart.domain.usecase.usecaseManager.user

import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.LogoutUserUseCase
import org.the_chance.honeymart.domain.usecase.RegisterUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateAuthenticationFieldsUseCase
import javax.inject.Inject

data class UserAuthenticationManagerUseCase @Inject constructor(
    val loginUseCase: LoginUserUseCase,
    val registerUseCase: RegisterUserUseCase,
    val logoutUseCase: LogoutUserUseCase,
    val validationUseCase: ValidateAuthenticationFieldsUseCase
)
