package com.example.helloandroidagain.presentation.tournament.list

import com.example.helloandroidagain.data.model.Tournament

interface TournamentListContract {
    interface View {
        fun updateTournamentList(tournaments: List<Tournament>)
    }

    interface Presenter {
        fun attachView(view: View)
        fun onDestroyView()
        fun createTournament(tournament: Tournament)
        fun removeTournament(tournamentPosition: Int)
        fun saveTournaments()
    }
}