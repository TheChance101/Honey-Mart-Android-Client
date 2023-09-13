package org.the_chance.honeymart.domain.usecase.usecaseManager.owner

import org.the_chance.honeymart.domain.usecase.CheckAdminApproveUseCase
import org.the_chance.honeymart.domain.usecase.CreateOwnerAccountUseCase
import org.the_chance.honeymart.domain.usecase.LogOutOwnerUseCase
import org.the_chance.honeymart.domain.usecase.LoginOwnerUseCase
import javax.inject.Inject

data class OwnerAuthenticationManagerUseCase @Inject constructor(
    val loginOwnerUseCase: LoginOwnerUseCase,
    val checkAdminApprove: CheckAdminApproveUseCase,
    val logOutOwnerUseCase: LogOutOwnerUseCase,
    val createOwnerAccount: CreateOwnerAccountUseCase,
)