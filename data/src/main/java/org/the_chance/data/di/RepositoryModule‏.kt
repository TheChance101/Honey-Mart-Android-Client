package org.the_chance.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.data.repository.HoneyMartRepository
import org.the_chance.data.repository.HoneyMartRepositoryImp
import org.the_chance.data.source.remote.network.HoneyMartService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(
        apiService: HoneyMartService,
    ): HoneyMartRepository {
        return HoneyMartRepositoryImp(apiService)
    }
}