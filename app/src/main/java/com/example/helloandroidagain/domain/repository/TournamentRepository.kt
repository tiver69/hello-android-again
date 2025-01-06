package com.example.helloandroidagain.domain.repository

import com.example.helloandroidagain.data.model.Tournament
import io.reactivex.rxjava3.core.Observable

interface TournamentRepository {
    fun getTournaments(): Observable<List<Tournament>>
    fun addTournament(tournament: Tournament)
    fun removeTournament(tournamentPosition: Int)
    fun saveTournaments()
}