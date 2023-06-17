package org.the_chance.honeymart.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
interface AuthRepository {
    suspend fun loginUser(email: String, password: String): String

    suspend fun saveToken(token: String)
    suspend fun getToken():Flow<String?>
    suspend fun clearToke()

}