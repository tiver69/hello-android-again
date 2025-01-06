package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.domain.repository.TournamentRepository

class RemoveTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(tournamentPosition: Int) = repository.removeTournament(tournamentPosition)
}