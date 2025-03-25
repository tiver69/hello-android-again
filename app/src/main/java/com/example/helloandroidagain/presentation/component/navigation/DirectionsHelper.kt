package com.example.helloandroidagain.presentation.component.navigation

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.presentation.tournament.list.TournamentListFragmentDirections
import javax.inject.Inject

class DirectionsHelper @Inject constructor() {
    fun toExportTournamentDirection(tournament: Tournament) =
        TournamentListFragmentDirections.navToExportTournament(tournament)

    fun toCreateTournamentDirection(nextTournamentPosition: Int) =
        TournamentListFragmentDirections.navToCreateTournament(nextTournamentPosition)
}