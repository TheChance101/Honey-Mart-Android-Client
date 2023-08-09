package org.the_chance.honeymart.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.repository.AuthRepositoryImp
import org.the_chance.honeymart.data.repository.HoneyMartRepositoryImp
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Singleton
    @Provides
    fun bindHoneyMartRepository(
        honeyMartService: HoneyMartService,
    ): HoneyMartRepository {
        return HoneyMartRepositoryImp(honeyMartService)
    }

    @Singleton
    @Provides
    fun bindAuthRepository(
        authDataStorePreferencesImp: AuthDataStorePreferences,
        honeyMartService: HoneyMartService,
    ): AuthRepository {
        return AuthRepositoryImp(authDataStorePreferencesImp, honeyMartService)
    }
}