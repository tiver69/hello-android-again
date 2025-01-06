package com.example.helloandroidagain.domain.usecase

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.repository.TournamentRepository
import io.reactivex.rxjava3.core.Observable

class LoadTournamentsUseCase(private val repository: TournamentRepository) {
    fun invoke(): Observable<List<Tournament>> =
        repository.getTournaments()
}
