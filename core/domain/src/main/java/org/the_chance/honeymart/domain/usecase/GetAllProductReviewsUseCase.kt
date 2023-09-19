package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllProductReviewsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long, page: Int): Reviews =
        honeyMartRepository.getReviewsForProduct(page, productId)
}