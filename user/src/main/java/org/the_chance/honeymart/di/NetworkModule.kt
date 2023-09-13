package org.the_chance.honeymart.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import okhttp3.logging.HttpLoggingInterceptor
import org.the_chance.honeymart.data.source.local.AuthorizationPreferences
import org.the_chance.honeymart.data.source.remote.network.AuthInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"
   // private const val BASE_URL = "http://192.168.1.3:8080"

    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true

            engine {
                addInterceptor(loggingInterceptor)
                addInterceptor(authInterceptor)
                config {
                    retryOnConnectionFailure(true)
                    connectTimeout(3, TimeUnit.MINUTES)
                    readTimeout(3, TimeUnit.MINUTES)
                    writeTimeout(3, TimeUnit.MINUTES)
                }
            }
            defaultRequest {
                url(BASE_URL)
                header(HttpHeaders.ContentType, "application/json")
            }

            install(ContentNegotiation) {
                gson()
            }
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(dataStorePreferences: AuthorizationPreferences): AuthInterceptor {
        return AuthInterceptor(dataStorePreferences)
    }
}
