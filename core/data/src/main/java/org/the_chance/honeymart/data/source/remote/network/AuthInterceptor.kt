package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePreferences: AuthDataStorePreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = dataStorePreferences.getToken()
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(
                AUTHORIZATION,
                "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTWFya2V0T3duZXIiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuTWFwQXBwbGljYXRpb25Db25maWdAM2IyZjRhOTMiLCJleHAiOjE2OTIyOTIzNjR9.fK899lYiH5s2hdKSarS5PNuwjs_J46_nBkG90zHLi5U"
            )
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}