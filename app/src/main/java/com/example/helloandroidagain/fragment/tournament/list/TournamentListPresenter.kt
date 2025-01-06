package com.example.helloandroidagain.fragment.tournament.list

import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.service.TournamentRepository
import com.example.helloandroidagain.usecase.CreateTournamentsUseCase
import com.example.helloandroidagain.usecase.LoadTournamentsUseCase
import com.example.helloandroidagain.usecase.RemoveTournamentsUseCase
import com.example.helloandroidagain.usecase.SaveTournamentsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TournamentListPresenter(tournamentRepository: TournamentRepository) :
    TournamentListContract.Presenter {
    private val loadTournamentsUseCase = LoadTournamentsUseCase(tournamentRepository)
    private val saveTournamentsUseCase = SaveTournamentsUseCase(tournamentRepository)
    private val createTournamentsUseCase = CreateTournamentsUseCase(tournamentRepository)
    private val removeTournamentsUseCase = RemoveTournamentsUseCase(tournamentRepository)
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