package com.example.helloandroidagain.tournament.presentation.di

import com.example.helloandroidagain.core.navigation.AuthNavigator
import com.example.helloandroidagain.tournament.presentation.navigation.AuthNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TournamentPresentationModule {

    @Binds
    fun provideAuthNavigator(impl: AuthNavigatorImpl): AuthNavigator
}
