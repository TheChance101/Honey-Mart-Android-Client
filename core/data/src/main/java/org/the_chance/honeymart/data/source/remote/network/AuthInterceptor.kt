package org.the_chance.honeymart.data.source.remote.network

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.domain.repository.AuthRepository
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStorePreferences: AuthDataStorePreferences,
    private val authRepository: AuthRepository,

    ) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = dataStorePreferences.getAccessToken()
        val refreshToken = dataStorePreferences.getRefreshToken()
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $accessToken")
            .build()

        val response = chain.proceed(newRequest)

        if (response.code == 401 && refreshToken != null) {
            runBlocking {
                val tokens = authRepository.refreshToken(refreshToken)
                dataStorePreferences.saveTokens(tokens.accessToken, tokens.refreshToken)

                val newAccessToken = tokens.accessToken
                val newRequestWithAccessToken = newRequest.newBuilder()
                    .removeHeader(AUTHORIZATION) // Remove the old authorization header
                    .addHeader(AUTHORIZATION, "Bearer $newAccessToken")
                    .build()

                return@runBlocking chain.proceed(newRequestWithAccessToken)

            }
        }


        return response
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}