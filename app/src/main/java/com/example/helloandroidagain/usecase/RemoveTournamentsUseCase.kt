package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.repository.TournamentRepository

class RemoveTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(tournamentPosition: Int) = repository.removeTournament(tournamentPosition)
}