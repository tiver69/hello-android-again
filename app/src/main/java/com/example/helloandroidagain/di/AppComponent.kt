package com.example.helloandroidagain.di

import com.example.helloandroidagain.presentation.tournament.create.TournamentCreateFragment
import com.example.helloandroidagain.presentation.tournament.list.TournamentListFragment
import dagger.Component

@AppScope
@Component(modules = [PresenterModule::class, AppModule::class, RepositoryModule::class])
interface AppComponent {
    fun injectTournamentCreateFragment(tournamentCreateFragment: TournamentCreateFragment)
    fun injectTournamentListFragment(tournamentListFragment: TournamentListFragment)
}