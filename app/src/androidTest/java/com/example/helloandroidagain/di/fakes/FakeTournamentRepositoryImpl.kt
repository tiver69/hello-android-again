package com.example.helloandroidagain.di.fakes

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.model.TournamentLogo
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import com.example.helloandroidagain.core.util.generateRandomDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

class FakeTournamentRepositoryImpl @Inject constructor() : TournamentRepository {

    companion object {
        val NAV_TOURNAMENT = Tournament(
            17L,
            "FakeNavTournament17",
            Random.nextInt(2, 10),
            generateRandomDate(),
            TournamentLogo.default()
        )
    }

    private val _tournamentsFlow: MutableStateFlow<List<Tournament>> =
        MutableStateFlow(generateTournaments())

    override fun getTournaments(): Flow<List<Tournament>> = _tournamentsFlow.asStateFlow()

    override suspend fun addTournament(tournament: Tournament) {
        val updatedTournamentsFlow = _tournamentsFlow.value + tournament
        _tournamentsFlow.value = updatedTournamentsFlow
    }

    override suspend fun removeTournament(id: Long) {
        val updatedTournamentsFlow = _tournamentsFlow.value.filter { tournament ->
            tournament.id != id
        }
        _tournamentsFlow.value = updatedTournamentsFlow
    }

    private fun generateTournaments(): List<Tournament> {
        val tournaments = (0..20).map {
            Tournament(
                it.toLong(),
                "FakeTournament$it",
                Random.nextInt(2, 10),
                generateRandomDate(),
                TournamentLogo.default()
            )
        }.toMutableList()
        tournaments[17] = NAV_TOURNAMENT
        return tournaments
    }
}