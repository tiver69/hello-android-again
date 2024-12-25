package com.example.helloandroidagain.fragment.tournament.list

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.service.TournamentService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TournamentListPresenter(
    private val tournamentService: TournamentService
) : TournamentListContract.Presenter {
    private var view: TournamentListContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: TournamentListContract.View) {
        this.view = view
        loadTournaments()
    }

    override fun detachView() {
        disposables.clear()
        this.view = null
    }

    override fun createTournament(tournament: Tournament) {
        tournamentService.addTournament(tournament)
    }

    override fun removeTournament(tournamentPosition: Int) {
        tournamentService.removeTournament(tournamentPosition)
    }

    override fun saveTournaments() {
        tournamentService.saveTournaments()
    }

    private fun loadTournaments() {
        val tournamentsDisposable = tournamentService.getTournaments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tournaments ->
                view?.updateTournamentList(tournaments)
            }
        disposables.add(tournamentsDisposable)
    }
}