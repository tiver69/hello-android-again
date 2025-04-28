package com.example.helloandroidagain.tournament.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logo")
data class LogoEntity(
    @PrimaryKey
    val id: String,
    val rawUrl: String,
    val regularUrl: String,
    val thumbUrl: String,
    val thumbImage: ByteArray? = null,
)