package org.the_chance.honeymart.domain.usecase.usecaseManager.owner

import org.the_chance.honeymart.domain.usecase.AddCouponUseCase
import org.the_chance.honeymart.domain.usecase.GetNoCouponMarketProductsUseCase
import org.the_chance.honeymart.domain.usecase.SearchNoCouponMarketProductsUseCase
import org.the_chance.honeymart.domain.usecase.ValidateOwnerFieldsUseCase
import javax.inject.Inject

data class OwnerCouponsManagerUseCase @Inject constructor(
    val getNoCouponMarketProducts: GetNoCouponMarketProductsUseCase,
    val searchNoCouponMarketProducts: SearchNoCouponMarketProductsUseCase,
    val addCoupon: AddCouponUseCase,
    val validationUseCase: ValidateOwnerFieldsUseCase
)