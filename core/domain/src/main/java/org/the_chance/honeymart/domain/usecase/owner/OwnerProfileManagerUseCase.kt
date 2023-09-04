package org.the_chance.honeymart.domain.usecase.owner

import org.the_chance.honeymart.domain.usecase.GetMarketInfoUseCase
import org.the_chance.honeymart.domain.usecase.GetOwnerProfileUseCase
import org.the_chance.honeymart.domain.usecase.UpdateMarketStatusUseCase
import javax.inject.Inject

class OwnerProfileManagerUseCase @Inject constructor(
    val getMarketInfoUseCase: GetMarketInfoUseCase,
    val getOwnerProfileUseCase: GetOwnerProfileUseCase,
    val updateMarketStatusUseCase: UpdateMarketStatusUseCase
)