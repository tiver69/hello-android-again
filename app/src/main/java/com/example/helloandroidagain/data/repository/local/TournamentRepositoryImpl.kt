package com.example.helloandroidagain.data.repository.local

import androidx.room.withTransaction
import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.data.db.StorageDatabase
import com.example.helloandroidagain.data.db.TournamentDao
import com.example.helloandroidagain.data.mapper.LogoMapper
import com.example.helloandroidagain.data.mapper.TournamentMapper
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
    private val storageDatabase: StorageDatabase
) :
    TournamentRepository {

    override fun getTournaments(): Flow<List<Tournament>> =
        tournamentDao.getAllTournaments().map { entityList ->
            entityList.map { tournament ->
                val logoEntity = logoDao.getLogoById(tournament.logoId)
                TournamentMapper.toDomain(tournament, LogoMapper.toDomain(logoEntity))
            }
        }

    override suspend fun addTournament(tournament: Tournament) {
        storageDatabase.withTransaction {
            tournamentDao.insertTournament(TournamentMapper.toData(tournament))
            logoDao.insertLogo(LogoMapper.toData(tournament.logo))
        }
    }

    override suspend fun removeTournament(id: Long) {
        tournamentDao.deleteTournamentById(id)
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