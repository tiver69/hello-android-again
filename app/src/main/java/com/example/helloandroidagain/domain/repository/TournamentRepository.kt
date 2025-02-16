package com.example.helloandroidagain.domain.repository

import com.example.helloandroidagain.data.model.Tournament
import kotlinx.coroutines.flow.StateFlow

interface TournamentRepository {
    fun getTournaments(): StateFlow<List<Tournament>>
    fun addTournament(tournament: Tournament)
    fun removeTournament(tournamentPosition: Int)
    suspend fun saveTournaments()
}