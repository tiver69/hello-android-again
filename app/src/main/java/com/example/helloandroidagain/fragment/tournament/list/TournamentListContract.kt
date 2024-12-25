package com.example.helloandroidagain.fragment.tournament.list

import com.example.helloandroidagain.model.Tournament

interface TournamentListContract {
    interface View {
        fun updateTournamentList(tournaments: List<Tournament>)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun createTournament(tournament: Tournament)
        fun removeTournament(tournamentPosition: Int)
        fun saveTournaments()
    }
}