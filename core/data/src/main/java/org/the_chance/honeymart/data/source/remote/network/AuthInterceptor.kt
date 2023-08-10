package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePref: AuthDataStorePref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = dataStorePref.getToken()
//        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5IiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTWFya2V0T3duZXIiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuTWFwQXBwbGljYXRpb25Db25maWdAM2IyZjRhOTMiLCJleHAiOjE2OTEyNzg2OTJ9.-72K2FS07Lc10ydc-cHYpxznCHtk_rMfa1_QeNLKgvY"
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5IiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTWFya2V0T3duZXIiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuTWFwQXBwbGljYXRpb25Db25maWdAM2IyZjRhOTMiLCJleHAiOjE2OTE2MTUxMzd9.LRL9E6mrZkX83SDjuveumTOyg7bUu0sP0QUBYVz4c7M")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}