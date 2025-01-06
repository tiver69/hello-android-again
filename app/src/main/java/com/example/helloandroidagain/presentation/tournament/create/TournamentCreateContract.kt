package com.example.helloandroidagain.presentation.tournament.create

import com.example.helloandroidagain.data.model.TournamentLogo

interface TournamentCreateContract {
    interface View {
        fun loadLogo(logoUrl: String)
        fun loadPlaceholderImage()
        fun showLogoErrorToast()
    }

    interface Presenter {
        fun attachView(
            view: View,
            preloadedLogosPosition: Int,
            tournamentLogosPage: Int
        )
        fun getCurrentLogo(): TournamentLogo
        fun getCurrentPreloadedPosition(): Int
        fun getCurrentLogosPage(): Int
        fun onDestroyView()
        fun regenerateTournamentLogo()
        fun fetchTournamentLogoPage()
    }
}