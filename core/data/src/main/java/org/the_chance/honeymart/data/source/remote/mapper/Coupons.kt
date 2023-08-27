package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CouponDto
import org.the_chance.honeymart.domain.model.Coupon
import org.the_chance.honeymart.domain.model.Product

internal fun CouponDto.toCoupon()= Coupon(
    couponId = couponId ?: 0L,
    count = count ?: 0,
    discountPercentage = discountPercentage ?: 0.0,
    expirationDate = expirationDate ?: "",
    product = product?.toProduct()?: Product(
        productId = 0L,
        productName = "",
        productDescription = "",
        productPrice = 0.0,
        productImages = emptyList()
    ),
    isClipped = isClipped ?: false,
    isUsed = isUsed ?: false
)