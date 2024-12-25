package com.example.helloandroidagain.fragment.tournament.create

import com.example.helloandroidagain.model.TournamentLogo

interface TournamentCreateContract {
    interface View {
        fun loadLogo(logo: TournamentLogo)
        fun loadPlaceholderImage()
        fun showLogoErrorToast()
    }

    interface Presenter {
        fun attachView(
            view: View,
            preloadedLogosPosition: Int,
            tournamentLogosPage: Int
        )
        fun getCurrentState(): Pair<Int, Int>
        fun detachView()
        fun regenerateTournamentLogo()
        fun fetchTournamentLogoPage()
    }
}