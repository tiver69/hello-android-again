package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.repository.TournamentRepository

class CreateTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(tournament: Tournament) = repository.addTournament(tournament)
}