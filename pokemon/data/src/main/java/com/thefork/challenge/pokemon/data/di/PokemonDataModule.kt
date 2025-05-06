package com.thefork.challenge.pokemon.data.di

import com.thefork.challenge.pokemon.data.repository.PokemonRepositoryImpl
import com.thefork.challenge.pokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PokemonDataModule {

    @Singleton
    @Binds
    fun providePokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}