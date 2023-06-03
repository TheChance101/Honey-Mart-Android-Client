package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.the_chance.data.source.remote.network.HoneyMartService
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor (
    private val honeyMartService: HoneyMartService
) : HoneyMartRepository {
    override fun getAllMart(): Flow<List<MarketDto>> = flow {
        val response = honeyMartService.getMarkets()
        emit(response.body()!!.value)
    }

}