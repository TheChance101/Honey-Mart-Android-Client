package org.the_chance.honeymart.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.local.AppDataStorePreferences
import org.the_chance.honeymart.data.source.local.AppDataStorePreferencesImp
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferencesImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAuthDataStorePreferences(@ApplicationContext context: Context): AuthDataStorePreferences {
        return AuthDataStorePreferencesImp(context)
    }

    @Singleton
    @Provides
    fun provideAppDataStorePreferences(@ApplicationContext context: Context): AppDataStorePreferences {
        return AppDataStorePreferencesImp(context)
    }
}