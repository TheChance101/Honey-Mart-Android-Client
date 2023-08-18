package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend fun addToCart(productId: Long, count: Int): String =
        honeyMartRepository.addToCart(productId, count)

    suspend fun deleteFromCart(productId: Long): String =
        honeyMartRepository.deleteFromCart(productId)

    suspend fun getCart(): CartEntity =
        honeyMartRepository.getCart()
}
