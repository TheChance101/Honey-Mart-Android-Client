package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketApprovalDto
import org.the_chance.honeymart.domain.model.MarketApproval

fun MarketApprovalDto.toMarketApproval(): MarketApproval {
    return MarketApproval(
        isMarketApproved = isMarketApproved ?: false,
        marketId = marketId ?: 0L
    )
}