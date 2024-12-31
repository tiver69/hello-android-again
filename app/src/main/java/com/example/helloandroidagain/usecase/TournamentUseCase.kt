package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.repository.TournamentRepository
import io.reactivex.rxjava3.core.Observable

class LoadTournamentsUseCase(private val repository: TournamentRepository) {
    fun execute(): Observable<List<Tournament>> =
        repository.getTournaments()
}

class SaveTournamentsUseCase(private val repository: TournamentRepository) {
    fun execute() = repository.saveTournaments()
}

class CreateTournamentsUseCase(private val repository: TournamentRepository) {
    fun execute(tournament: Tournament) = repository.addTournament(tournament)
}

class RemoveTournamentsUseCase(private val repository: TournamentRepository) {
    fun execute(tournamentPosition: Int) = repository.removeTournament(tournamentPosition)
}