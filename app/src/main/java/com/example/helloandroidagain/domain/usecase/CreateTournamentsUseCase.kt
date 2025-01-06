package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.TournamentRepository

class CreateTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(tournament: Tournament) = repository.addTournament(tournament)
}