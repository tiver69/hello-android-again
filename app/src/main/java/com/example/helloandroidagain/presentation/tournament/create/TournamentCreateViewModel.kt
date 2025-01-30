package com.example.helloandroidagain.presentation.tournament.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.data.repository.remote.TOURNAMENT_LOGO_PER_PAGE
import com.example.helloandroidagain.domain.usecase.FetchTournamentLogoPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _currentLogoUrl: MutableStateFlow<Result<String>> =
        MutableStateFlow(Result.success(currentLogo.regularUrl))
    val currentLogoUrl = _currentLogoUrl.asStateFlow()

    fun regenerateTournamentLogo() {
        if (preloadedLogos.isEmpty()) {
            fetchTournamentLogoPage(tournamentLogosPage)
        } else if (preloadedLogosPosition < TOURNAMENT_LOGO_PER_PAGE) {
            currentLogo = preloadedLogos[preloadedLogosPosition++]
            _currentLogoUrl.value = Result.success(currentLogo.regularUrl)
        } else {
            preloadedLogosPosition = 0
            fetchTournamentLogoPage(tournamentLogosPage)
        }
    }

    fun fetchTournamentLogoPage() {
        fetchTournamentLogoPage(tournamentLogosPage)
    }

    private fun fetchTournamentLogoPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                preloadedLogos = fetchTournamentLogoPageUseCase.invoke(page)
                tournamentLogosPage++
                currentLogo = preloadedLogos[preloadedLogosPosition++]
                _currentLogoUrl.value = Result.success(currentLogo.regularUrl)
            } catch (e: Exception) {
                preloadedLogos = emptyList()
                currentLogo = TournamentLogo.default()
                _currentLogoUrl.value = Result.failure(e)
                Log.e("TournamentCreateVM", "Error while loading new logo page", e)
            }
        }
    }
}