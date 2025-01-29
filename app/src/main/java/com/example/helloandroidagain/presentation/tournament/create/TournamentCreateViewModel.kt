package com.example.helloandroidagain.presentation.tournament.create

import androidx.lifecycle.ViewModel
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.TOURNAMENT_LOGO_PER_PAGE
import com.example.helloandroidagain.domain.usecase.FetchTournamentLogoPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class TournamentCreateViewModel @Inject constructor(
    private var fetchTournamentLogoPageUseCase: FetchTournamentLogoPageUseCase
) : ViewModel() {
    private var preloadedLogos: List<TournamentLogo> = emptyList()
    var currentLogo: TournamentLogo = TournamentLogo.default()
        private set
        get() = field.copy()
    private var preloadedLogosPosition: Int = 0
    private var tournamentLogosPage: Int = 1

    private val _currentLogoUrl = BehaviorSubject.create<String>()
    val currentLogoUrl: Observable<String> = _currentLogoUrl.hide()

    private val _logoError = PublishSubject.create<Unit>()
    val logoError: Observable<Unit> = _logoError.hide()
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    fun regenerateTournamentLogo() {
        if (preloadedLogos.isEmpty()) {
            fetchTournamentLogoPage(tournamentLogosPage)
        } else if (preloadedLogosPosition < TOURNAMENT_LOGO_PER_PAGE) {
            currentLogo = preloadedLogos[preloadedLogosPosition++]
            _currentLogoUrl.onNext(currentLogo.regularUrl)
        } else {
            preloadedLogosPosition = 0
            fetchTournamentLogoPage(tournamentLogosPage)
        }
    }

    fun fetchTournamentLogoPage() {
        fetchTournamentLogoPage(tournamentLogosPage)
    }

    private fun fetchTournamentLogoPage(page: Int) {
        fetchTournamentLogoPageUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { logos ->
                    preloadedLogos = logos
                    tournamentLogosPage++
                    currentLogo = preloadedLogos[preloadedLogosPosition++]
                    _currentLogoUrl.onNext(currentLogo.regularUrl)
                },
                {
                    preloadedLogos = emptyList()
                    currentLogo = TournamentLogo.default()
                    _logoError.onNext(Unit)
                })
            .also {
                disposables.add(it)
            }
    }
}