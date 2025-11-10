package com.example.cocktailapp.di

import com.example.cocktailapp.data.repository.CocktailRepositoryImpl
import com.example.cocktailapp.domain.repository.CocktailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository

}