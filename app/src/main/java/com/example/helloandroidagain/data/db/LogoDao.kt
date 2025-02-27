package com.example.helloandroidagain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.helloandroidagain.data.db.entity.LogoEntity

@Dao
interface LogoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogo(logo: LogoEntity)

    @Query("DELETE FROM logo WHERE id = :logoId")
    suspend fun deleteLogo(logoId: String)
}