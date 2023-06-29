package org.the_chance.honeymart.di


import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.the_chance.honeymart.data.source.local.AuthDataStorePref
import org.the_chance.honeymart.data.source.remote.network.AuthInterceptor
import org.the_chance.honeymart.data.source.remote.network.CacheInterceptor
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app/"

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
        cacheInterceptor: CacheInterceptor,
        application: Application
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            val cacheDirectory = File(application.cacheDir, Constant.HTTP_CACHE)
            val cache = Cache(cacheDirectory, Constant.MAX_SIZE)
            addInterceptor(loggingInterceptor)
            addInterceptor(headerInterceptor)
            addNetworkInterceptor(cacheInterceptor)
            cache(cache)
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
    @Singleton
    @Provides
    fun provideCacheInterceptor(): CacheInterceptor = CacheInterceptor()
}