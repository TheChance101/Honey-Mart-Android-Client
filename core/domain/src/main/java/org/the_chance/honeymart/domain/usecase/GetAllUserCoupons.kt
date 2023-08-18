package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllUserCoupons @Inject constructor(
    private val service: HoneyMartRepository
) {
    suspend operator fun invoke() = service.getUSerCoupons()

}