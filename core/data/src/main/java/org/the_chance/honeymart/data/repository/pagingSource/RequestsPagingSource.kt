package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.mapper.toRequestEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.RequestEntity
import javax.inject.Inject

class RequestsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val isApproved: Boolean
) : BasePagingSource<RequestEntity>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<RequestEntity> {
        return wrap { honeyMartService.getMarketRequests(isApproved = isApproved) }.value?.map { it.toRequestEntity() }
            ?: throw Throwable("paging error")
    }

}
