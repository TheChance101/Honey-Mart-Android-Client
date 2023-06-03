package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto
import kotlinx.coroutines.flow.Flow

interface HoneyMartRepository {

     fun getAllMart(): Flow<List<MarketDto>>


}