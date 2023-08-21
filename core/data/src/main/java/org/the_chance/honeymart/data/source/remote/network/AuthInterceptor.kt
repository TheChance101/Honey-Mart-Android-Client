package org.the_chance.honeymart.data.source.remote.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.the_chance.honeymart.data.BuildConfig
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePreferences: AuthDataStorePreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = dataStorePreferences.getAccessToken()
        val refreshToken = dataStorePreferences.getRefreshToken()
        val oldRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                AUTHORIZATION,
                "Bearer $accessToken"
            )
            .addHeader(API_KEY, BuildConfig.API_KEY)
            .build()

        val oldResponse = chain.proceed(oldRequest)
        if (oldResponse.code == 401 && refreshToken != "" && refreshToken != null) {
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("refreshToken", refreshToken)
                .build()
            val newRequest = Request.Builder()
                .post(formBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .url("${BASE_URL}token/refresh")
                .build()
            val response = client.newCall(newRequest).execute()
            if (response.code == 200) {
                val jsonData = response.body.string()
                val gson = Gson()
                try {
                    val type = object : TypeToken<BaseResponse<UserLoginDto>>() {}.type
                    val tokensResponse: BaseResponse<UserLoginDto> = gson.fromJson(jsonData, type)
                    runBlocking {
                        val tokens = tokensResponse.value!!
                        dataStorePreferences.saveTokens(tokens.accessToken!!, tokens.refreshToken!!)
                        val newAccessToken = tokens.accessToken
                        val newRequestWithAccessToken = oldRequest.newBuilder()
                            .removeHeader(AUTHORIZATION)
                            .addHeader(AUTHORIZATION, "Bearer $newAccessToken")
                            .build()

                        return@runBlocking chain.proceed(newRequestWithAccessToken)
                    }
                } catch (e: Exception) {
                    Log.e("refreshResponse if", "intercept: ${e.message}")
                }
            }
        }
        return oldResponse
    }

    companion object {
        private const val API_KEY = "apiKey"
        private const val AUTHORIZATION = "Authorization"
        private const val BASE_URL = "http://10.0.2.2:8080/"
    }
}