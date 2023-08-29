package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllMarketsPagingUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): Flow<PagingData<MarketEntity>> {
        return honeyMartRepository.getAllMarketsPaging(null)
    }

}