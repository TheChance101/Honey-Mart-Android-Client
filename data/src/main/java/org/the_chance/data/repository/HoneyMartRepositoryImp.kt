package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HoneyMartRepositoryImp (
    private val honeyMartApi:
) : HoneyMartRepository {
    private val honeyMartService = HoneyMartApi.honeyMartService
    override fun getAllMart(): Flow<List<MarketDto>> = flow {
        val response = honeyMartService.getMarkets()
        emit(response.body()!!.value)
    }

}