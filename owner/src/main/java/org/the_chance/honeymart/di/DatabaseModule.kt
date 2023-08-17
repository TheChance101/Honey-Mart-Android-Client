package org.the_chance.honeymart.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferencesImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAuthDataStorePref(@ApplicationContext context: Context): AuthDataStorePreferences {
        return AuthDataStorePreferencesImp(context)

    }
}