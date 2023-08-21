package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetCouponsUseCase @Inject constructor(
    private val service: HoneyMartRepository
) {
    suspend fun getAllValidCoupons() = service.getAllValidCoupons()
    suspend fun getUserCoupons() = service.getUserCoupons()
}