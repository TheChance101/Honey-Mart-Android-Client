package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetCouponsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend fun getAllValidCoupons() = honeyMartRepository.getAllValidCoupons()
    suspend fun getUserCoupons() = honeyMartRepository.getUserCoupons()
}