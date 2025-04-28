package com.example.helloandroidagain.tournament.domain.repository

import com.example.helloandroidagain.tournament.domain.model.TournamentLogo

interface ImageRemoteRepository {
    suspend fun getImagePage(page: Int): List<TournamentLogo>
}