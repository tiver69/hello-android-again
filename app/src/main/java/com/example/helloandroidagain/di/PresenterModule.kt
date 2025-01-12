package com.example.helloandroidagain.di

import com.example.helloandroidagain.presentation.tournament.create.TournamentCreateContract
import com.example.helloandroidagain.presentation.tournament.create.TournamentCreatePresenter
import com.example.helloandroidagain.presentation.tournament.list.TournamentListContract
import com.example.helloandroidagain.presentation.tournament.list.TournamentListPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class PresenterModule {
    @Binds
    abstract fun bindTournamentCreatePresenter(
        tournamentCreatePresenter: TournamentCreatePresenter
    ): TournamentCreateContract.Presenter

    @Binds
    abstract fun bindTournamentListPresenter(
        tournamentListPresenter: TournamentListPresenter
    ): TournamentListContract.Presenter
}