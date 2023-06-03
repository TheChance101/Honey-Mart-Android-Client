package org.the_chance.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import org.the_chance.data.repository.HoneyMartRepository
import org.the_chance.data.repository.HoneyMartRepositoryImp

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindHoneyMartRepository(
        honeyMartRepository: HoneyMartRepositoryImp,
    ): HoneyMartRepository

}