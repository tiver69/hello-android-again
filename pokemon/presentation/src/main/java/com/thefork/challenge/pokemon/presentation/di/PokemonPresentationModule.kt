package com.thefork.challenge.pokemon.presentation.di

import com.thefork.challenge.common.navigation.PokemonNavigator
import com.thefork.challenge.pokemon.presentation.navigation.PokemonNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PokemonPresentationModule {

    @Binds
    @Singleton
    abstract fun bindPokemonNavigator(impl: PokemonNavigatorImpl): PokemonNavigator
}