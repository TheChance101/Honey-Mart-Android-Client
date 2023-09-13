package org.the_chance.honeymart.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.the_chance.honeymart.domain.usecase.IValidationUseCase
import org.the_chance.honeymart.domain.usecase.ValidationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun providesValidationUseCase(useCase: ValidationUseCase): IValidationUseCase
}