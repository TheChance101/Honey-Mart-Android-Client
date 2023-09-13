package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toMarket
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.Market
import javax.inject.Inject

class MarketsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
) : BasePagingSource<Market>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<Market> {
        return wrap { honeyMartService.getAllMarketsPaging(page = page) }.value?.map { it.toMarket() }
            ?: throw Throwable("paging error")
    }
}
