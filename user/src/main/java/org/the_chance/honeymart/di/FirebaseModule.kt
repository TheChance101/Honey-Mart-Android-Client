package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.remote.network.FireBaseMsgService
import org.the_chance.honeymart.data.source.remote.network.FireBaseMsgServiceImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {


    @Binds
    abstract fun bindsFireBaseMsgService(firebaseMessaging: FireBaseMsgServiceImpl): FireBaseMsgService


}