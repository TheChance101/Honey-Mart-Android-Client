package org.the_chance.honeymart.domain.usecase.usecaseManager.admin

import org.the_chance.honeymart.domain.usecase.CheckAdminAuthenticationUseCase
import org.the_chance.honeymart.domain.usecase.LoginAdminUseCase
import org.the_chance.honeymart.domain.usecase.ValidationAdminLoginFieldsUseCase
import javax.inject.Inject

data class AdminAuthManagerUseCase @Inject constructor(
    val login: LoginAdminUseCase,
    val validationLogin: ValidationAdminLoginFieldsUseCase,
    val checkAuthentication: CheckAdminAuthenticationUseCase
)
