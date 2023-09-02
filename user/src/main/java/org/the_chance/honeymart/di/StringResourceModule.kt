package org.the_chance.honeymart.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.util.StringDictionary
import org.the_chance.honeymart.util.StringResources
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StringResourceModule {
    @Singleton
    @Provides
    fun provideStringResource(context: Context): StringDictionary {
        return StringResources(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}