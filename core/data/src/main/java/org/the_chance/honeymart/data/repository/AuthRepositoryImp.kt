package org.the_chance.honeymart.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val datastore: AuthDataStorePref,
) : AuthRepository {
    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.toString()

    override suspend fun saveToken(token: String) {
        datastore.saveToken(token)
        Log.e("Saved Successfully : ", "$token")
    }

    override suspend fun getToken(): Flow<String?> {
        return datastore.getToken()
    }

    override suspend fun clearToken() {
        datastore.clearToken()
    }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body()?.value as T
        } else {
            throw Throwable("Unknown error occurred")
        }
    }

    override suspend fun addUser(
        fullName: String,
        password: String,
        email: String,
    ): Boolean? {

        val response = honeyMartService.addUser(fullName, password, email)
        try {
            return if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.isSuccess == true) {
                    response.body()?.isSuccess
                } else {
                    Log.e("TAG", "addUser error: ${response.code()}")
                    throw Throwable(response.message())
                }
            } else {
                throw Throwable(response.message())
            }
        } catch (t: Throwable) {
            Log.e("TAG", "addUser error: ${t.message}")
            throw Throwable(t.message)
        }
    }
}