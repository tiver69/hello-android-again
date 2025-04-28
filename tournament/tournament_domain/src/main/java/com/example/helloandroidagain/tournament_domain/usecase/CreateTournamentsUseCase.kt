package com.example.helloandroidagain.tournament_domain.usecase

import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.repository.TournamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend fun invoke(tournament: Tournament) = withContext(Dispatchers.IO) {
        repository.addTournament(tournament)
    }
}
