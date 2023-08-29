package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.remote.network.FireBaseMessageService
import org.the_chance.honeymart.data.source.remote.network.FireBaseMsgServiceImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class FirebaseModule {
    @Singleton
    @Binds
    abstract fun bindsFireBaseMsgService(firebaseMessaging: FireBaseMsgServiceImpl): FireBaseMessageService


}