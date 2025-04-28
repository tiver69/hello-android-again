package com.example.helloandroidagain.tournament.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tournament")
data class TournamentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val participantCount: Int,
    val date: LocalDate,
    val logoId: String
)