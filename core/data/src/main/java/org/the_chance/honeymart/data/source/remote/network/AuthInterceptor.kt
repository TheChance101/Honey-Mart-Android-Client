package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.BuildConfig
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePreferences: AuthDataStorePreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = dataStorePreferences.getToken()
       val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDQyYjg0Mjg2IiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0AzY2Q5YWE2NCIsImV4cCI6MTY5MjY0MjY2NiwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4ifQ.awmAHViSNG4Jdpt12qtCDNyAD_r1u2Ezmco6iNDNLnc"
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(API_KEY, BuildConfig.API_KEY)
            .addHeader(AUTHORIZATION, "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "apiKey"
        private const val AUTHORIZATION = "Authorization"
    }
}