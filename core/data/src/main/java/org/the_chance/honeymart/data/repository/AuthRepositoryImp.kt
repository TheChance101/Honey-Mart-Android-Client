package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.mapper.toUserLoginEntity
import org.the_chance.honeymart.data.source.remote.network.FireBaseMsgService
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
    private val fireBaseMsgService: FireBaseMsgService
) : BaseRepository(), AuthRepository {

    override suspend fun createUserAccount(
        fullName: String,
        password: String,
        email: String,
    ): Boolean =
        wrap { honeyMartService.addUser(fullName, password, email) }.isSuccess

    override suspend fun getDeviceToken(): String {
        return fireBaseMsgService.getDeviceToken()
    }


    override suspend fun loginUser(email: String, password: String,deviceToken:String): UserLoginEntity =
        wrap { honeyMartService.loginUser(email, password,deviceToken) }.value?.toUserLoginEntity()
            ?: throw NotFoundException()

    override suspend fun saveToken(token: String) {
        datastore.saveToken(token)
        Log.e("Saved Successfully : ", token)
    }

    override fun getToken(): String? {
        return datastore.getToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }


}