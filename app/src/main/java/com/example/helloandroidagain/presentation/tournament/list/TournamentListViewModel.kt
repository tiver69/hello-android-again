package com.example.helloandroidagain.presentation.tournament.list

import androidx.lifecycle.ViewModel
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.RemoveTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.SaveTournamentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TournamentListViewModel @Inject constructor(
    private var loadTournamentsUseCase: LoadTournamentsUseCase,
    private var saveTournamentsUseCase: SaveTournamentsUseCase,
    private var createTournamentsUseCase: CreateTournamentsUseCase,
    private var removeTournamentsUseCase: RemoveTournamentsUseCase,
) : ViewModel() {

    private val _tournaments = BehaviorSubject.create<List<Tournament>>()
    val tournaments: Observable<List<Tournament>> = _tournaments.hide()

    private val disposables = CompositeDisposable()

    fun loadTournaments() {
        loadTournamentsUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _tournaments.onNext(it)
            }
            .also { disposables.add(it) }
    }

    fun createTournament(tournament: Tournament) {
        createTournamentsUseCase.invoke(tournament)
    }

    fun removeTournament(tournamentPosition: Int) {
        removeTournamentsUseCase.invoke(tournamentPosition)
    }

    fun saveTournaments() {
        saveTournamentsUseCase.invoke()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}