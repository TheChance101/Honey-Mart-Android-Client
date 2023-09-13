package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toReview
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.Review
import javax.inject.Inject

class ReviewsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val productId: Long,
) : BasePagingSource<Review>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<Review> {
        return wrap {
            honeyMartService.getAllProductReviewsPaging(page = page, productId = productId)
        }.value?.map { it.toReview() }
            ?: throw Throwable("paging error")
    }
}
