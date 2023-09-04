package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

data class OwnerCouponsManagerUseCase @Inject constructor(
    val getNoCouponMarketProducts: GetNoCouponMarketProductsUseCase,
    val searchNoCouponMarketProducts: SearchNoCouponMarketProductsUseCase,
    val addCoupon: AddCouponUseCase
)