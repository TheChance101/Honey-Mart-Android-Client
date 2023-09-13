package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.Review
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllProductReviewsPagingUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(productId: Long): Flow<PagingData<Review>> {
        return honeyMartRepository.getAllProductReviewsPaging(page = null, productId)
    }
}