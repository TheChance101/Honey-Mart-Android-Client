package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthInterceptor @Inject constructor(
    private val dataStorePref: AuthDataStorePref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val testToken =
     "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDVlYzQ2Y2RkIiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0AyMWQxYjMyMSIsImV4cCI6MTY4NzQ0NDYxNX0.1CQvgFH6z7qMc8I38euEviS55152uhIAluvhwHkwKUg"
             val token = dataStorePref.getToken()
            val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $testToken")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}