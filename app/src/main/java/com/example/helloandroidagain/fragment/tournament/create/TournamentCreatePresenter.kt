package com.example.helloandroidagain.fragment.tournament.create

import com.example.helloandroidagain.model.TournamentLogo
import com.example.helloandroidagain.service.ImageRemoteService
import com.example.helloandroidagain.service.RetrofitInstance
import com.example.helloandroidagain.service.TOURNAMENT_LOGO_PER_PAGE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TournamentCreatePresenter : TournamentCreateContract.Presenter {
    private val imageRemoteService =
        RetrofitInstance.retrofit.create(ImageRemoteService::class.java)
    private var view: TournamentCreateContract.View? = null
    private lateinit var preloadedLogos: List<TournamentLogo>
    private val disposables = CompositeDisposable()
    private var currentLogo: TournamentLogo = TournamentLogo.default()
    private var preloadedLogosPosition: Int = 0
    private var tournamentLogosPage: Int = 1

    override fun attachView(
        view: TournamentCreateContract.View,
        preloadedLogosPosition: Int,
        tournamentLogosPage: Int
    ) {
        this.view = view
        this.preloadedLogosPosition = preloadedLogosPosition
        this.tournamentLogosPage = tournamentLogosPage
    }

    override fun getCurrentLogo(): TournamentLogo = currentLogo.copy()

    override fun getCurrentPreloadedPosition(): Int = preloadedLogosPosition - 1

    override fun getCurrentLogosPage(): Int = tournamentLogosPage - 1

    override fun onDestroyView() {
        this.view = null
        disposables.clear()
    }

    override fun regenerateTournamentLogo() {
        if (preloadedLogos.isEmpty()) {
            fetchTournamentLogoPage(tournamentLogosPage)
        } else if (preloadedLogosPosition < TOURNAMENT_LOGO_PER_PAGE) {
            currentLogo = preloadedLogos[preloadedLogosPosition++]
            view?.loadLogo(currentLogo.regularUrl)
        } else {
            preloadedLogosPosition = 0
            fetchTournamentLogoPage(tournamentLogosPage)
        }
    }

    override fun fetchTournamentLogoPage() {
        fetchTournamentLogoPage(tournamentLogosPage)
    }

    private fun fetchTournamentLogoPage(page: Int) {
        val logoDisposable =
            imageRemoteService.searchLogo(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { logos ->
                        preloadedLogos = logos
                        tournamentLogosPage++
                        currentLogo = preloadedLogos[preloadedLogosPosition++]
                        view?.loadLogo(currentLogo.regularUrl)
                    },
                    {
                        preloadedLogos = emptyList()
                        currentLogo = TournamentLogo.default()
                        view?.loadPlaceholderImage()
                        view?.showLogoErrorToast()
                    })
        disposables.add(logoDisposable)
    }
}