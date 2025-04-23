package com.example.helloandroidagain.tournament_domain.usecase

import com.example.helloandroidagain.tournament_domain.repository.TournamentRepository
import javax.inject.Inject

class RemoveTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend fun invoke(id: Long) = repository.removeTournament(id)
}