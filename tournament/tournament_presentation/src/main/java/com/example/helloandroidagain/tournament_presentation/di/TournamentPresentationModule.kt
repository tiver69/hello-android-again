package com.example.helloandroidagain.tournament_presentation.di

import com.example.helloandroidagain.core.navigation.AuthNavigator
import com.example.helloandroidagain.tournament_presentation.navigation.AuthNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TournamentPresentationModule {

    @Provides
    fun provideAuthNavigator(): AuthNavigator = AuthNavigatorImpl()
}