package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName
data class ValidCouponDto(
    @SerializedName("couponId")
    val couponId: Long? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("discountPercentage")
    val discountPercentage: Double? = null,
    @SerializedName("expirationDate")
    val expirationDate: String? = null,
    @SerializedName("product")
    val product: ProductDto? = null
)

