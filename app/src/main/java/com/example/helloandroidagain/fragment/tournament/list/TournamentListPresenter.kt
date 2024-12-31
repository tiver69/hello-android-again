package com.example.helloandroidagain.fragment.tournament.list

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.service.TournamentService
import com.example.helloandroidagain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.usecase.RemoveTournamentsUseCase
import com.example.helloandroidagain.usecase.SaveTournamentsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TournamentListPresenter(tournamentService: TournamentService) :
    TournamentListContract.Presenter {
    private val loadTournamentsUseCase = LoadTournamentsUseCase(tournamentService)
    private val saveTournamentsUseCase = SaveTournamentsUseCase(tournamentService)
    private val createTournamentsUseCase = CreateTournamentsUseCase(tournamentService)
    private val removeTournamentsUseCase = RemoveTournamentsUseCase(tournamentService)
    private var view: TournamentListContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: TournamentListContract.View) {
        this.view = view
        val tournamentsDisposable = loadTournamentsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tournaments ->
                view.updateTournamentList(tournaments)
            }
        disposables.add(tournamentsDisposable)
    }

    override fun detachView() {
        disposables.clear()
        this.view = null
    }

    override fun createTournament(tournament: Tournament) {
        createTournamentsUseCase.execute(tournament)
    }

    override fun removeTournament(tournamentPosition: Int) {
        removeTournamentsUseCase.execute(tournamentPosition)
    }

    override fun saveTournaments() {
        saveTournamentsUseCase.execute()
    }
}