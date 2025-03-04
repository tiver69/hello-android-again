package com.example.helloandroidagain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helloandroidagain.data.db.entity.LogoEntity

@Dao
interface LogoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLogo(logo: LogoEntity)

    @Query("UPDATE logo SET thumbImage = :image WHERE thumbUrl = :thumbUrl")
    suspend fun updateCacheByThumbUrl(thumbUrl: String, image: ByteArray)

    @Query("SELECT thumbImage FROM logo WHERE thumbUrl = :thumbUrl")
    suspend fun getCachedLogoByThumbUrl(thumbUrl: String): ByteArray?

    @Query("SELECT * FROM logo WHERE id = :logoId")
    suspend fun getLogoById(logoId: String): LogoEntity
}