package org.the_chance.honeymart.data.repository.pagingSource

//class ReviewsPagingSource @Inject constructor(
//    honeyMartService: HoneyMartService,
//    val productId: Long,
//) : BasePagingSource<Review>(honeyMartService) {
//    override suspend fun fetchData(page: Int): List<Review> {
//        return wrap {
//            honeyMartService.getAllProductReviewsPaging(page = page, productId = productId)
//        }.value?.map { it.toReview() }
//            ?: throw Throwable("paging error")
//    }
//}
