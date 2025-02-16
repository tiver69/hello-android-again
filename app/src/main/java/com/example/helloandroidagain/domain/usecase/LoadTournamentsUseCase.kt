package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoadTournamentsUseCase @Inject constructor(private val repository: TournamentRepository) {
    fun invoke(): StateFlow<List<Tournament>> = repository.getTournaments()
}
