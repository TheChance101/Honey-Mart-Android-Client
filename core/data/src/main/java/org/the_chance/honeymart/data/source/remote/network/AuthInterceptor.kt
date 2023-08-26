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
        val (oldRequest, oldResponse) = makeRequest(chain, accessToken)
        if (oldResponse.code == 401 && refreshToken != "" && refreshToken != null) {
            val response = refreshToken(refreshToken)
            if (response.code == 200) {
                retryRequest(response, oldRequest, chain)
            }
        }
        return oldResponse
    }

    private fun makeRequest(
        chain: Interceptor.Chain,
        accessToken: String?
    ): Pair<Request, Response> {
        val oldRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                AUTHORIZATION,
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDRjMWJkY2MyIiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0A3MmY5ZjI3YyIsImV4cCI6MTY5MzE2NDA5MywidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4ifQ.YtRZxrAEponbv6K4HK7ni1YoZnpprthWG6JfY1kOtl8"
            )
            .addHeader(API_KEY, BuildConfig.API_KEY)
            .build()

        val oldResponse = chain.proceed(oldRequest)
        return Pair(oldRequest, oldResponse)
    }

    private fun refreshToken(refreshToken: String): Response {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("refreshToken", refreshToken)
            .build()
        val newRequest = Request.Builder()
            .post(formBody)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .url("${BASE_URL}token/refresh")
            .build()
        return client.newCall(newRequest).execute()
    }

    private fun retryRequest(
        response: Response,
        oldRequest: Request,
        chain: Interceptor.Chain
    ) {
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


    companion object {
        private const val API_KEY = "apiKey"
        private const val AUTHORIZATION = "Authorization"
        private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"
    }
}