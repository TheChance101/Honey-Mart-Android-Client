package org.the_chance.honeymart.domain.model

data class CouponEntity(
    val couponId: Long ,
    val count: Int  ,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: ProductEntity,
    val isClipped: Boolean,
    val isUsed: Boolean
)


