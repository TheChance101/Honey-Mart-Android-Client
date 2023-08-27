package org.the_chance.honeymart.domain.model

data class Coupon(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: Product,
    val isClipped: Boolean,
    val isUsed: Boolean
)


