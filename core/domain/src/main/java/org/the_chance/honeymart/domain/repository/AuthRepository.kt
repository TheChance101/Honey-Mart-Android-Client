package org.the_chance.honeymart.domain.repository

import org.the_chance.honeymart.domain.model.UserLoginEntity

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
interface AuthRepository {
    suspend fun loginUser(email: String, password: String,deviceToken:String): UserLoginEntity

    suspend fun saveToken(token: String)
    fun getToken(): String?
    suspend fun clearToken()

    suspend fun createUserAccount(fullName: String, password: String, email: String): Boolean

    suspend fun getDeviceToken(): String

}