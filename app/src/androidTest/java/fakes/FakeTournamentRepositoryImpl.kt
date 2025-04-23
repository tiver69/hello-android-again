package fakes

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.domain.repository.TournamentRepository
import com.example.helloandroidagain.core.util.generateRandomDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class FakeTournamentRepositoryImpl : TournamentRepository {

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