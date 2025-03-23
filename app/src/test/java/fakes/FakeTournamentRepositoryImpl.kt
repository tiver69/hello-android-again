package fakes

import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeTournamentRepositoryImpl : TournamentRepository {

    override fun getTournaments(): Flow<List<Tournament>> =
        flowOf(
            listOf(
                Tournament(
                    0,
                    "Test1",
                    10,
                    LocalDate.now(),
                    TournamentLogo("a", "raw", "regular", "thumb")
                )
            )
        )

    override suspend fun addTournament(tournament: Tournament) {

    }

    override suspend fun removeTournament(id: Long) {

    }
}