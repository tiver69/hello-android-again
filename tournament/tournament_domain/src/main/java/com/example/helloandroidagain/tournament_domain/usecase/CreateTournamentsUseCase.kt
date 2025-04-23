package com.example.helloandroidagain.tournament_domain.usecase

import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.repository.TournamentRepository
import javax.inject.Inject

class CreateTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend fun invoke(tournament: Tournament) = repository.addTournament(tournament)
}