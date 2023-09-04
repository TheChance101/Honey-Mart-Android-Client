package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

data class OwnerAuthenticationManagerUseCase @Inject constructor(
    val loginOwnerUseCase: LoginOwnerUseCase,
    val checkAdminApprove: CheckAdminApproveUseCase,
    val logOutOwnerUseCase: LogOutOwnerUseCase,
    val createOwnerAccount: CreateOwnerAccountUseCase,
)