package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.the_chance.honeymart.data.repository.HoneyMartRepository
import org.the_chance.honeymart.data.repository.HoneyMartRepositoryImp

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindHoneyMartRepository(
        honeyMartRepository: HoneyMartRepositoryImp,
    ): HoneyMartRepository

}