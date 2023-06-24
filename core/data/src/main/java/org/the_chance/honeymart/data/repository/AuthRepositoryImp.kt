package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidEmailOrPasswordException
import org.the_chance.honeymart.domain.util.InvalidFullNameException
import org.the_chance.honeymart.domain.util.InvalidPasswordException
import org.the_chance.honeymart.domain.util.InvalidRegisterException
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val datastore: AuthDataStorePref,
) : AuthRepository {

//    override suspend fun addUser(fullName: String, password: String, email: String): String =
//        wrap { honeyMartService.addUser(fullName, password, email) }
//

    override suspend fun loginUser(email: String, password: String): String =
        wrap { honeyMartService.loginUser(email, password) }.toString()

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

//    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
//        val response = function()
//        return if (response.isSuccessful) {
//            response.body()?.value as T
//        } else {
//            when (response.code()) {
//                400 -> throw InvalidPasswordException()
//                400 -> throw InvalidEmailException()
//                400 -> throw InvalidFullNameException()
//                else -> throw Exception(response.message())
//            }
//        }
//    }

    private suspend fun <T> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            if (response.body()?.isSuccess == true) {
                response.body()?.value as T
            } else {
                when (response.body()?.status?.code) {
                    409 -> throw InvalidEmailOrPasswordException()
                    400 -> throw InvalidRegisterException()
                    1001 -> throw InvalidEmailException()
                    else -> throw Exception(response.message())
                }
            }
        } else {
            throw Exception(response.message())
        }
    }
    override suspend fun addUser(
        fullName: String,
        password: String,
        email: String,
    ): Boolean? {

        val response = honeyMartService.addUser(fullName, password, email)

        return if (response.isSuccessful) {
            response.body()?.isSuccess
        } else {
            when (response.code()) {
                400 -> throw InvalidPasswordException()
                400 -> throw InvalidEmailException()
                400 -> throw InvalidFullNameException()
                else -> throw Exception(response.message())
            }
            false
        }

    }
}