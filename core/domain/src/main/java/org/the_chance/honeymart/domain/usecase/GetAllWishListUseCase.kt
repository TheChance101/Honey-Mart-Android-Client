package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.WishList
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllWishListUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): List<WishList>
    {
        return honeyMartRepository.getWishList()
    }

}