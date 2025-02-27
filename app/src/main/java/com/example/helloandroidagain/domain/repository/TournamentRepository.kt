package com.example.helloandroidagain.domain.repository

import com.example.helloandroidagain.data.model.Tournament
import kotlinx.coroutines.flow.Flow

interface TournamentRepository {
    fun getTournaments(): Flow<List<Tournament>>
    suspend fun addTournament(tournament: Tournament)
    suspend fun removeTournament(id: Long)
}