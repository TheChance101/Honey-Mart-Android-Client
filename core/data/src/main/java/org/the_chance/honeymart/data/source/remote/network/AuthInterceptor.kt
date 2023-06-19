package org.the_chance.honeymart.data.source.remote.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
class AuthInterceptor @Inject constructor(
    private val dataStorePref: AuthDataStorePref,
) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newRequest: Request = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${dataStorePref.getToken()}")
            .build()
        return chain.proceed(newRequest)
    }

}