package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.mapper.toUserLoginEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.UserLoginEntity
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val datastore: AuthDataStorePreferences,
    private val honeyMartService: HoneyMartService,
) : BaseRepository(), AuthRepository {

    override suspend fun createUserAccount(
        fullName: String,
        password: String,
        email: String,
    ): Boolean =
        wrap { honeyMartService.addUser(fullName, password, email) }.isSuccess


    override suspend fun loginUser(email: String, password: String): UserLoginEntity =
        wrap { honeyMartService.loginUser(email, password) }.value?.toUserLoginEntity()
            ?: throw NotFoundException()

    override suspend fun refreshToken(refreshToken: String): LoginEntity =
        wrap { honeyMartService.refreshToken(refreshToken) }.value?.toLoginEntity()
            ?: throw NotFoundException()


    override suspend fun saveTokens(accessToken: String,refreshToken: String) {
        datastore.saveTokens(accessToken, refreshToken)
        Log.e("Saved  Tokens Successfully : ", "$accessToken $refreshToken",)
    }

    override fun getAccessToken(): String? {
        return datastore.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return datastore.getRefreshToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }


}