package com.example.helloandroidagain.usecase

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.repository.TournamentRepository
import io.reactivex.rxjava3.core.Observable

class LoadTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(): Observable<List<Tournament>> =
        repository.getTournaments()
}
