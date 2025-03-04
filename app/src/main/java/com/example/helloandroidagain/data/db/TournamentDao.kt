package com.example.helloandroidagain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helloandroidagain.data.db.entity.TournamentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournament(tournament: TournamentEntity): Long

    @Query("SELECT * FROM tournament")
    fun getAllTournaments(): Flow<List<TournamentEntity>>

    @Query("DELETE FROM tournament WHERE id = :tournamentId")
    suspend fun deleteTournamentById(tournamentId: Long)
}