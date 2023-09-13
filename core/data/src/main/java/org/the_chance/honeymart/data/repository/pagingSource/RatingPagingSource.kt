package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toRating
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.Rating
import javax.inject.Inject


class RatingPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val id: Long,
) : BasePagingSource<Rating>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<Rating> {
        return wrap {
            honeyMartService.getAllRatingsForProduct(
                page = page,
                productId = id
            )
        }.value?.map { it.toRating() }
            ?: throw Throwable("paging error")
    }

}

