package com.example.helloandroidagain.tournament.data.repository.local

import androidx.room.withTransaction
import com.example.helloandroidagain.tournament.data.db.LogoDao
import com.example.helloandroidagain.tournament.data.db.StorageDatabase
import com.example.helloandroidagain.tournament.data.db.TournamentDao
import com.example.helloandroidagain.tournament.data.mapper.LogoMapper
import com.example.helloandroidagain.tournament.data.mapper.TournamentMapper
import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TournamentRepositoryImpl @Inject constructor(
    private val tournamentDao: TournamentDao,
    private val logoDao: LogoDao,
    private val storageDatabase: StorageDatabase
) : TournamentRepository {

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
}