package com.example.helloandroidagain.tournament.domain.usecase

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    fun invoke(): Flow<List<Tournament>> = repository.getTournaments()
}
