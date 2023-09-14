package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import javax.inject.Inject


//class RatingPagingSource @Inject constructor(
//    honeyMartService: HoneyMartService,
//    val id: Long,
//) : BasePagingSource<ReviewStatistic>(honeyMartService) {
//    override suspend fun fetchData(page: Int): List<ReviewStatistic> {
//        return wrap {
//            honeyMartService.getReviewsForProduct(
//                page = page,
//                productId = id
//            )
//        }.value?.map { it.toRating() }
//            ?: throw Throwable("paging error")
//    }
//
//}
//
