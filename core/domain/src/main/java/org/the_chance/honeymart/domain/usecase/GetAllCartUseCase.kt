package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Cart
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllCartUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke():Cart =
        honeyMartRepository.getCart()
}
