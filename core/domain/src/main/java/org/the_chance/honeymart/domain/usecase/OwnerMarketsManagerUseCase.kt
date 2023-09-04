package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

data class OwnerMarketsManagerUseCase @Inject constructor(
    val getOwnerMarketId: GetOwnerInfoUseCase,
    val addMarketImageUseCase: AddMarketImageUseCase,
    val createMarketUseCase: CreateMarketUseCase,
)