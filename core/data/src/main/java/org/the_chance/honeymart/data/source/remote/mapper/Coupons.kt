package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CouponDto
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.ProductEntity

internal fun CouponDto.toCouponEntity()= CouponEntity(
    couponId = couponId ?: 0L,
    count = count ?: 0,
    discountPercentage = discountPercentage ?: 0.0,
    expirationDate = expirationDate ?: "",
    product = product?.toProductEntity()?: ProductEntity(
        productId = 0L,
        productName = "",
        productDescription = "",
        productPrice = 0.0,
        productImages = emptyList()
    ),
    isClipped = isClipped ?: false,
    isUsed = isUsed ?: false
)