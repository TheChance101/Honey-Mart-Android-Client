package org.the_chance.honeymart.domain.usecase.usecaseManager.user

import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetIfProductInWishListUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import javax.inject.Inject

data class UserWishListManagerUseCase @Inject constructor(
    val operationWishListUseCase: WishListOperationsUseCase,
    val getAllWishListUseCase: GetAllWishListUseCase,
    val getIfProductInWishListUseCase: GetIfProductInWishListUseCase,
)
