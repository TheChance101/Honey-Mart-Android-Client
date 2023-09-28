package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class WishListOperationsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend  fun deleteFromWishList(productId: Long): String =
        honeyMartRepository.deleteFromWishList(productId)
     suspend fun addToWishList(productId: Long): String =
         honeyMartRepository.addToWishList(productId)
}