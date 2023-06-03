package data.remote.repository

import data.remote.models.MarketDto
import data.remote.network.HoneyMartApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//class HoneyMartRepositoryImp : HoneyMartRepository {
//    private val honeyMartService = HoneyMartApi.honeyMartService
//    override fun getAllMart(): Flow<List<MarketDto>> = flow {
//        val response = honeyMartService.getMarkets()
//        emit(response.body()!!.value)
//    }
//
//}