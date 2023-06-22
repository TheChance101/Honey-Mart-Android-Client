package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.WishListEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetIfProductInWishListUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {

    suspend operator fun invoke(productId: Long): Boolean =
        filterProductById(honeyMartRepository.getWishList(), productId)

    private fun filterProductById(
        products: List<WishListEntity>,
        productId: Long
    ): Boolean = products.map { it.productId }.contains(productId)
}