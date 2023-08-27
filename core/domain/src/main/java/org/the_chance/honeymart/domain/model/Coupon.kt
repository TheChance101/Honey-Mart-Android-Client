package org.the_chance.honeymart.domain.model

import java.util.Date

data class Coupon(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: Date,
    val product: Product,
    val isClipped: Boolean,
    val isUsed: Boolean
)


