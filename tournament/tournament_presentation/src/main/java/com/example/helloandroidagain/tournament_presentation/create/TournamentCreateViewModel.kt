package com.example.helloandroidagain.tournament_presentation.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.core.util.TOURNAMENT_LOGO_PER_PAGE
import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.example.helloandroidagain.tournament_domain.usecase.FetchTournamentLogoPageUseCase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TournamentCreateViewModel @Inject constructor(
    private var fetchTournamentLogoPageUseCase: FetchTournamentLogoPageUseCase,
    private var analytics: FirebaseAnalytics
) : ViewModel() {

    private var preloadedLogos: List<TournamentLogo> = emptyList()
    private var preloadedLogosPosition: Int = 0
    private var tournamentLogosPage: Int = 1

    private val _currentLogo: MutableStateFlow<TournamentLogo?> =
        MutableStateFlow(TournamentLogo.default())
    val currentLogo = _currentLogo.asStateFlow()

    fun regenerateTournamentLogo() {
        if (preloadedLogos.isEmpty()) {
            fetchTournamentLogoPage(tournamentLogosPage)
        } else if (preloadedLogosPosition < TOURNAMENT_LOGO_PER_PAGE) {
            _currentLogo.value = preloadedLogos[preloadedLogosPosition++]
        } else {
            preloadedLogosPosition = 0
            fetchTournamentLogoPage(tournamentLogosPage)
        }
    }

    fun fetchTournamentLogoPage() {
        fetchTournamentLogoPage(tournamentLogosPage)
    }

    private fun fetchTournamentLogoPage(page: Int) {
        viewModelScope.launch {
            try {
                preloadedLogos = fetchTournamentLogoPageUseCase.invoke(page)
                tournamentLogosPage++
                _currentLogo.value = preloadedLogos[preloadedLogosPosition++]
            } catch (e: IOException) {
                preloadedLogos = emptyList()
                _currentLogo.value = null
                analytics.logEvent("unsplash_remote") {
                    param(FirebaseAnalytics.Param.CONTENT_TYPE, "page")
                    param(FirebaseAnalytics.Param.ITEM_ID, page.toDouble())
                    param(FirebaseAnalytics.Param.SUCCESS, "FALSE")
                }
                Log.e("TournamentCreateVM", "Error while loading new logo page", e)
            }
        }
    }
}