package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ValidCouponDto
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.ValidCouponEntity

internal fun ValidCouponDto.toValidCouponEntity()= ValidCouponEntity(
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

)