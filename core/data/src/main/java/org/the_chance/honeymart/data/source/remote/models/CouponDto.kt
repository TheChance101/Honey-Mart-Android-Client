package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CouponDto(
    @SerializedName("couponId")
    val couponId: Long? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("discountPercentage")
    val discountPercentage: Double? = null,
    @SerializedName("expirationDate")
    val expirationDate: Long? = null,
    @SerializedName("product")
    val product: ProductDto? = null,
    @SerializedName("isClipped")
    val isClipped: Boolean? = null,
    @SerializedName("isUsed")
    val isUsed: Boolean? = null
)

