package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePref: AuthDataStorePref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NiIsImF1ZCI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0AyMTNiZDNkNSIsIlJPTEVfVFlQRSI6Ik5vcm1hbFVzZXIiLCJpc3MiOiJpby5rdG9yLnNlcnZlci5jb25maWcuTWFwQXBwbGljYXRpb25Db25maWdAM2IyZjRhOTMiLCJleHAiOjE2OTA5ODk1OTZ9.pldDYBQwuYvxFI63JvKOv5v-M9oLNX4CGwcOUgPUDxM"
        //val token = dataStorePref.getToken()
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Bearer yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiaW8ua3Rvci5zZXJ2ZXIuY29uZmlnLk1hcEFwcGxpY2F0aW9uQ29uZmlnQDIxM2JkM2Q1IiwiUk9MRV9UWVBFIjoiTm9ybWFsVXNlciIsImlzcyI6ImlvLmt0b3Iuc2VydmVyLmNvbmZpZy5NYXBBcHBsaWNhdGlvbkNvbmZpZ0AzYjJmNGE5MyIsImV4cCI6MTY5MDk4NTA5Nn0.zM7S1Vivu0mbisk6RYyJa_sD8BPqjICGRieImNeBZvw")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}