package org.the_chance.honeymart.data.source.local

import kotlinx.coroutines.flow.Flow

interface AuthDataStorePref {
    suspend fun saveToken(token: String)
    suspend fun getToken(): Flow<String?>
    suspend fun clearToken()
}