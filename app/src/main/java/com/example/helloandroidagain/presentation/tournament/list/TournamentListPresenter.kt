package com.example.helloandroidagain.presentation.tournament.list

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.domain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.RemoveTournamentsUseCase
import com.example.helloandroidagain.domain.usecase.SaveTournamentsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TournamentListPresenter @Inject constructor() :
    TournamentListContract.Presenter {
    @Inject
    lateinit var loadTournamentsUseCase: LoadTournamentsUseCase

    @Inject
    lateinit var saveTournamentsUseCase: SaveTournamentsUseCase

    @Inject
    lateinit var createTournamentsUseCase: CreateTournamentsUseCase

    @Inject
    lateinit var removeTournamentsUseCase: RemoveTournamentsUseCase
    private var view: TournamentListContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: TournamentListContract.View) {
        this.view = view
        val tournamentsDisposable = loadTournamentsUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tournaments ->
                view.updateTournamentList(tournaments)
            }
        disposables.add(tournamentsDisposable)
    }

    override fun onDestroyView() {
        disposables.clear()
        this.view = null
    }

    override fun createTournament(tournament: Tournament) {
        createTournamentsUseCase.invoke(tournament)
    }

    override fun removeTournament(tournamentPosition: Int) {
        removeTournamentsUseCase.invoke(tournamentPosition)
    }

    override fun saveTournaments() {
        saveTournamentsUseCase.invoke()
    }
}