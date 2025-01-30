package com.example.helloandroidagain.presentation.tournament.list

import androidx.lifecycle.ViewModel
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.RemoveTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.SaveTournamentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TournamentListViewModel @Inject constructor(
    private var loadTournamentsUseCase: LoadTournamentsUseCase,
    private var saveTournamentsUseCase: SaveTournamentsUseCase,
    private var createTournamentsUseCase: CreateTournamentsUseCase,
    private var removeTournamentsUseCase: RemoveTournamentsUseCase,
) : ViewModel() {

    val tournamentsFlow: StateFlow<List<Tournament>> = loadTournamentsUseCase.invoke()

    fun createTournament(tournament: Tournament) {
        createTournamentsUseCase.invoke(tournament)
    }

    fun removeTournament(tournamentPosition: Int) {
        removeTournamentsUseCase.invoke(tournamentPosition)
    }

    fun saveTournaments() {
        saveTournamentsUseCase.invoke()
    }
}