package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.data.repository.AuthRepositoryImp
import org.the_chance.honeymart.data.repository.HoneyMartRepositoryImp
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.usecase.IValidationUseCase
import org.the_chance.honeymart.domain.usecase.ValidationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindHoneyMartRepository(repository: HoneyMartRepositoryImp): HoneyMartRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repository: AuthRepositoryImp): AuthRepository
}