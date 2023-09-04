package org.the_chance.honeymart.domain.usecase.owner

import org.the_chance.honeymart.domain.usecase.AddMarketImageUseCase
import org.the_chance.honeymart.domain.usecase.CreateMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetOwnerInfoUseCase
import javax.inject.Inject

data class OwnerMarketsManagerUseCase @Inject constructor(
    val getOwnerMarketId: GetOwnerInfoUseCase,
    val addMarketImageUseCase: AddMarketImageUseCase,
    val createMarketUseCase: CreateMarketUseCase,
)