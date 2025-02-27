package com.example.helloandroidagain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.helloandroidagain.data.db.entity.TournamentEntity
import com.example.helloandroidagain.data.db.entity.TournamentLogoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: TournamentEntity): Long

    @Transaction
    @Query("SELECT * FROM tournament")
    fun getAllTournamentsWithLogo(): Flow<List<TournamentLogoEntity>>

    @Query("DELETE FROM tournament WHERE id = :tournamentId")
    suspend fun deleteTournamentById(tournamentId: Long)
}