package data.remote.repository

import data.remote.models.MarketDto
import kotlinx.coroutines.flow.Flow

interface HoneyMartRepository {

     fun getAllMart(): Flow<List<MarketDto>>


}