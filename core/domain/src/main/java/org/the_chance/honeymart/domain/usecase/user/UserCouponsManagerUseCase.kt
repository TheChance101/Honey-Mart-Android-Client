package org.the_chance.honeymart.domain.usecase.user

import org.the_chance.honeymart.domain.usecase.ClipCouponUseCase
import org.the_chance.honeymart.domain.usecase.GetClippedUserCouponsUseCase
import org.the_chance.honeymart.domain.usecase.GetCouponsUseCase
import javax.inject.Inject

data class UserCouponsManagerUseCase @Inject constructor(
    val clipCouponUseCase: ClipCouponUseCase,
    val getAllCouponsUseCase: GetCouponsUseCase,
    val getClippedUserCouponsUseCase: GetClippedUserCouponsUseCase,
)
