package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

class OwnerProfileManagerUseCase @Inject constructor(
    val getMarketInfoUseCase: GetMarketInfoUseCase,
    val getOwnerProfileUseCase: GetOwnerProfileUseCase,
    val updateMarketStatusUseCase: UpdateMarketStatusUseCase
)