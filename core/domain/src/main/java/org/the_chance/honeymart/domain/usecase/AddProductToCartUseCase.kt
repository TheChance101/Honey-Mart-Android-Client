package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long, count: Long): String =
        honeyMartRepository.addProductToCart(productId, count)
}