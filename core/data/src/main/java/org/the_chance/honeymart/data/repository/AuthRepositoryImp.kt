package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val datastore: AuthDataStorePref,
    private val honeyMartService: HoneyMartService,
) : BaseRepository(), AuthRepository {

    override suspend fun createUserAccount(
        fullName: String,
        password: String,
        email: String,
    ): Boolean =
        wrap { honeyMartService.addUser(fullName, password, email) }.isSuccess


    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.value ?: throw NotFoundException()

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