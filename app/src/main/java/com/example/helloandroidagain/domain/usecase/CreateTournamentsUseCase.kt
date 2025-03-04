package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.TournamentRepository
import javax.inject.Inject

class CreateTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend fun invoke(tournament: Tournament) = repository.addTournament(tournament)
}