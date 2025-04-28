package com.example.helloandroidagain.tournament.presentation.navigation

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.presentation.list.TournamentListFragmentDirections
import javax.inject.Inject

class DirectionsHelper @Inject constructor() {
    fun toExportTournamentDirection(tournament: Tournament) =
        TournamentListFragmentDirections.navToExportTournament(tournament)

    fun toCreateTournamentDirection(nextTournamentPosition: Int) =
        TournamentListFragmentDirections.navToCreateTournament(nextTournamentPosition)
}