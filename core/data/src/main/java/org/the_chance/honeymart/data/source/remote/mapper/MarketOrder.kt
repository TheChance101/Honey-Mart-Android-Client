package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.MarketOrderDto
import org.the_chance.honeymart.data.source.remote.models.UserDto
import org.the_chance.honeymart.domain.model.MarketOrderEntity
import org.the_chance.honeymart.domain.model.UserEntity

fun MarketOrderDto.toMarketOrderEntity(): MarketOrderEntity {
    return MarketOrderEntity(
        orderId = orderId ?: 0L,
        totalPrice = totalPrice ?: 0.0,
        state = state ?: 0,
        date = date ?: 0L,
        user = user.toUserEntity(),
    )
}

fun UserDto.toUserEntity() = UserEntity(
    userId = userId,
    fullName = fullName
)
