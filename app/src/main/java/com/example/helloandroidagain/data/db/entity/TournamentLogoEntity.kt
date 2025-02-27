package com.example.helloandroidagain.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TournamentLogoEntity(
    @Embedded
    val tournament: TournamentEntity,

    @Relation(
        parentColumn = "logoId",
        entityColumn = "id"
    )
    val logo: LogoEntity
)