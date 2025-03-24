package fakes

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.domain.repository.TournamentRepository
import com.example.helloandroidagain.util.generateRandomDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class FakeTournamentRepositoryImpl : TournamentRepository {

    override fun getTournaments(): Flow<List<Tournament>> = flowOf(generateTournaments())

    override suspend fun addTournament(tournament: Tournament) {

    }

    override suspend fun removeTournament(id: Long) {

    }

    private fun generateTournaments(): List<Tournament> = (0..20).map {
        Tournament(
            0L,
            "Tournament$it",
            Random.nextInt(2, 10),
            generateRandomDate(),
            TournamentLogo.default()
        )
    }.toMutableList()
}