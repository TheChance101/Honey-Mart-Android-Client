package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllMarketsPagingUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(page:Int): List<Market>? {
        return honeyMartRepository.getAllMarketsPaging(page)
    }
}