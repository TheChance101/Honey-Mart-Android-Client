package org.the_chance.honeymart.di

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirBaseInstanceModule {

    @Provides
    fun provideInstance(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}