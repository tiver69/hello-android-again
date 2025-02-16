package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.domain.repository.TournamentRepository
import javax.inject.Inject

class SaveTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    suspend fun invoke() = repository.saveTournaments()
}