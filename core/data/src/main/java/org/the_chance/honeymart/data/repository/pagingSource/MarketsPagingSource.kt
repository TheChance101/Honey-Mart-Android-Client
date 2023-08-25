package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.MarketEntity
import javax.inject.Inject

class MarketsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
) : BasePagingSource<MarketEntity>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<MarketEntity> {
        return wrap { honeyMartService.getAllMarketsPaging(page = page) }.value?.map { it.toMarketEntity() }
            ?: throw Throwable("paging error")
    }

}
