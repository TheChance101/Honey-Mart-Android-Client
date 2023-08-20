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
        val token = dataStorePreferences.getToken()
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(API_KEY, BuildConfig.API_KEY)
            .addHeader(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTWFya2V0T3duZXIiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuTWFwQXBwbGljYXRpb25Db25maWdAM2IyZjRhOTMiLCJleHAiOjE2OTI1NDIwNzN9.T8svAyZWhFuEyOi4FbQQeXQT16oiNh_vrserSQKbh0U")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "apiKey"
        private const val AUTHORIZATION = "Authorization"
    }
}