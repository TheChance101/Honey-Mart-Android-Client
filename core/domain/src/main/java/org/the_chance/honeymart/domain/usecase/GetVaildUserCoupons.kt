package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetVaildUserCoupons @Inject constructor(
    private val service: HoneyMartRepository
) { suspend operator fun invoke() = service.getValidUSerCoupons() }