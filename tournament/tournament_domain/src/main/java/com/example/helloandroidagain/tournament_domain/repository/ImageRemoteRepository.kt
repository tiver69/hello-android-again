package com.example.helloandroidagain.tournament_domain.repository

import com.example.helloandroidagain.tournament_domain.model.TournamentLogo

interface ImageRemoteRepository {
    suspend fun getImagePage(page: Int): List<TournamentLogo>
}