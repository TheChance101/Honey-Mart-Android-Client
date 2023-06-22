package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllCartUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke():CartEntity =
        honeyMartRepository.getCart()
}
