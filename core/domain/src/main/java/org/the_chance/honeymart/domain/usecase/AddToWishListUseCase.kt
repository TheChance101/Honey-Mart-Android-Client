package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddToWishListUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(productId: Long): String {
        return honeyMartRepository.addToWishList(productId)
    }
}