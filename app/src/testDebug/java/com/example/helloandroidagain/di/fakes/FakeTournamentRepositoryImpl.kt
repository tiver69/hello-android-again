package com.example.helloandroidagain.di.fakes

import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.model.TournamentLogo
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTournamentRepositoryImpl : TournamentRepository {

    override fun getTournaments(): Flow<List<Tournament>> =
        flowOf(
            listOf(
                Tournament(
                    0,
                    "ActiveTest",
                    10,
                    LocalDate.now().plusDays(1),
                    TournamentLogo("a", "raw", "regular", "thumb")
                ),
                Tournament(
                    0,
                    "OutdatedTest",
                    10,
                    LocalDate.now().minusDays(1),
                    TournamentLogo("a", "raw", "regular", "thumb")
                )
            )
        )

    override suspend fun addTournament(tournament: Tournament) {
    }

    override suspend fun removeTournament(id: Long) {
    }
}