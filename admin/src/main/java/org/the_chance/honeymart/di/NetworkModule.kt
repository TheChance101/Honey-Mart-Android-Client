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
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.network.AuthInterceptor
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.data.source.remote.network.HoneyMartServiceImp
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"
//    private const val BASE_URL = "http://10.0.2.2:8080/"

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
                    connectTimeout(1, TimeUnit.MINUTES)
                    readTimeout(1, TimeUnit.MINUTES)
                    writeTimeout(1, TimeUnit.MINUTES)
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
    fun provideHoneyMartService(httpClient: HttpClient): HoneyMartService {
        return HoneyMartServiceImp(httpClient)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(dataStorePref: AuthDataStorePreferences): AuthInterceptor {
        return AuthInterceptor(dataStorePref)
    }

}