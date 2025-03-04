package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    fun invoke(): Flow<List<Tournament>> = repository.getTournaments()
}
