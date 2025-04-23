package com.example.helloandroidagain.tournament_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.helloandroidagain.tournament_data.db.entity.LogoEntity
import com.example.helloandroidagain.tournament_data.db.entity.TournamentEntity
import com.example.helloandroidagain.core.util.convertToLocalDate
import com.example.helloandroidagain.core.util.convertToString
import java.time.LocalDate

@Database(
    entities = [TournamentEntity::class, LogoEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentDao
    abstract fun logoDao(): LogoDao
}

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.convertToString()

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? = dateString?.convertToLocalDate()
}