package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toMarketRequest
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.MarketRequest
import javax.inject.Inject

class RequestsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val isApproved: Boolean
) : BasePagingSource<MarketRequest>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<MarketRequest> {
        return wrap { honeyMartService.getMarketsRequests(isApproved = isApproved) }.value?.map { it.toMarketRequest() }
            ?: throw Throwable("paging error")
    }

}
