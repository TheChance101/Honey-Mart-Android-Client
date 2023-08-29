package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SearchNoCouponMarketProductsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(query: String): List<Product> {
        return honeyMartRepository.searchNoCouponMarketProducts(query)
    }
}