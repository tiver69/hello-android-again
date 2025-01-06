package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.repository.TournamentRepository

class SaveTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke() = repository.saveTournaments()
}