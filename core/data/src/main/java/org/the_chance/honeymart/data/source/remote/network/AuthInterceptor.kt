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
       val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDRjMWJkY2MyIiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0A3MmY5ZjI3YyIsImV4cCI6MTY5MjgwMDc0MCwidG9rZW5UeXBlIjoiYWNjZXNzVG9rZW4ifQ.aFO5ZVGvaJOTtsOdDntF2jlFolZlUOhLfCpjVll1USE"
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