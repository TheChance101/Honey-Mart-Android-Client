package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePref: AuthDataStorePref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
val fakeToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0AzYjJmNGE5MyIsImV4cCI6MTY5MDg5NzY2Mn0.QdkT3bPbHyy7H07-4TfO3KCjOQNtrCNU0gPL_o5NmeQ"
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $fakeToken")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}