package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.data.source.remote.network.HoneyMartServiceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkBindingsModule {
    @Singleton
    @Binds
    abstract fun bindHoneyMartService(honeyMartServiceImp: HoneyMartServiceImp): HoneyMartService
}