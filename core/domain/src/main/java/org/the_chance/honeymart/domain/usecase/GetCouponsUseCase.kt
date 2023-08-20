package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetCouponsUseCase @Inject constructor(
    private val service: HoneyMartRepository
) {
    suspend fun getValidUsersCoupon() = service.getValidUSerCoupons()

     suspend fun getAllCoupons() = service.getUserCoupons()

}