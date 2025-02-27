package com.example.helloandroidagain.data.repository.local

import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.data.db.TournamentDao
import com.example.helloandroidagain.data.mapper.TournamentLogoMapper
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.domain.repository.TournamentRepository
import com.example.helloandroidagain.util.generateRandomDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class TournamentRepositoryImpl @Inject constructor(
    private val tournamentDao: TournamentDao,
    private val logoDao: LogoDao,
) :
    TournamentRepository {

    override fun getTournaments(): Flow<List<Tournament>> =
        tournamentDao.getAllTournamentsWithLogo().map { list ->
            list.map { entity -> TournamentLogoMapper.mapEntityToTournament(entity) }
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

    override suspend fun addTournament(tournament: Tournament) {
        logoDao.insertLogo(tournament.toLogoEntity())
        tournamentDao.insertTournament(tournament.toNewEntity())
    }

    override suspend fun removeTournament(id: Long) {
        tournamentDao.deleteTournamentById(id)
    }
}