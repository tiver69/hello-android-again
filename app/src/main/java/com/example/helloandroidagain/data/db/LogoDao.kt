package com.example.helloandroidagain.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface LogoDao {

    @Query("UPDATE logo SET thumbImage = :image WHERE thumbUrl = :thumbUrl")
    suspend fun updateCacheByThumbUrl(thumbUrl: String, image: ByteArray)

    @Query("SELECT thumbImage FROM logo WHERE thumbUrl = :thumbUrl")
    suspend fun getCachedLogoByThumbUrl(thumbUrl: String): ByteArray?
}