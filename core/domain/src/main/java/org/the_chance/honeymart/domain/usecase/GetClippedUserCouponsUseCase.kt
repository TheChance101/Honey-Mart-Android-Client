package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetClippedUserCouponsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke():  List<CouponEntity>
    {
        return honeyMartRepository.getClippedUserCoupons()
    }
}