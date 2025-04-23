package com.example.helloandroidagain.presentation.tournament.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_domain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.tournament_domain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.tournament_domain.usecase.RemoveTournamentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TournamentListViewModel @Inject constructor(
    private var loadTournamentsUseCase: LoadTournamentsUseCase,
    private var createTournamentsUseCase: CreateTournamentsUseCase,
    private var removeTournamentsUseCase: RemoveTournamentsUseCase,
) : ViewModel() {

    lateinit var tournamentsFlow: StateFlow<List<Tournament>>

    fun restoreTournaments() {
        viewModelScope.launch {
            tournamentsFlow = loadTournamentsUseCase.invoke()
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
        }
    }

    fun createTournament(tournament: Tournament) {
        viewModelScope.launch {
            createTournamentsUseCase.invoke(tournament)
        }
    }

    fun removeTournament(tournamentPosition: Int) {
        viewModelScope.launch {
            removeTournamentsUseCase.invoke(tournamentsFlow.value[tournamentPosition].id)
        }
    }
}
