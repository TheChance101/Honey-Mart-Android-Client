package org.the_chance.honeymart.domain.usecase.usecaseManager.user

import org.the_chance.honeymart.domain.usecase.CartUseCase
import org.the_chance.honeymart.domain.usecase.CheckoutUseCase
import org.the_chance.honeymart.domain.usecase.DeleteAllCartUseCase
import javax.inject.Inject

data class CartProductsManagerUseCase @Inject constructor(

    val cartUseCase: CartUseCase,
    val checkout: CheckoutUseCase,
    val deleteAllCartUseCase: DeleteAllCartUseCase,
)
