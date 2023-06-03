package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto

interface HoneyMartRepository {

    suspend fun getAllMarket(): List<MarketDto>?

}