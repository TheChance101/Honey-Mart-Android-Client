package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class AddCouponUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    @Suppress("SimpleDateFormat")
    suspend operator fun invoke(
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: Date,
    ) {
        val expirationDateString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expirationDate)

        honeyMartRepository.addCoupon(
            productId,
            count,
            discountPercentage,
            expirationDateString
        )
    }
}