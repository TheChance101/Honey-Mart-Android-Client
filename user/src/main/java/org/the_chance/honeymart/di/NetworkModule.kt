package org.the_chance.honeymart.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.network.AuthInterceptor
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"
    //  private const val BASE_URL = "http://10.0.2.2:8080/"

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: AuthInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(headerInterceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideHoneyMartService(retrofit: Retrofit): HoneyMartService =
        retrofit.create(HoneyMartService::class.java)

    @Singleton
    @Provides
    fun provideHeaderInterceptor(dataStorePref: AuthDataStorePref): AuthInterceptor {
        return AuthInterceptor(dataStorePref)
    }

}