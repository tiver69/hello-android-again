package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.domain.repository.TournamentRepository

class SaveTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke() = repository.saveTournaments()
}