package com.example.helloandroidagain.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.data.db.TournamentDao
import com.example.helloandroidagain.data.db.StorageDatabase
import com.example.helloandroidagain.data.repository.local.ImageCacheRepositoryImpl
import com.example.helloandroidagain.data.repository.local.ExportRepositoryImpl
import com.example.helloandroidagain.data.repository.local.TournamentRepositoryImpl
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.data.repository.remote.ImageRetrofitInstance
import com.example.helloandroidagain.domain.repository.ImageCacheRepository
import com.example.helloandroidagain.domain.repository.ExportRepository
import com.example.helloandroidagain.domain.repository.TournamentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    companion object {
        private const val STORAGE_DB = "application_storage_db"
    }

    @Singleton
    @Provides
    fun provideImageCacheRepository(logoDao: LogoDao): ImageCacheRepository =
        ImageCacheRepositoryImpl(logoDao)

    @Singleton
    @Provides
    fun provideTournamentRepository(
        tournamentDao: TournamentDao,
        logoDao: LogoDao,
        storageDatabase: StorageDatabase
    ): TournamentRepository = TournamentRepositoryImpl(tournamentDao, logoDao, storageDatabase)

    @Singleton
    @Provides
    fun provideExportRepository(
        contentResolver: ContentResolver
    ): ExportRepository = ExportRepositoryImpl(contentResolver)

    @Singleton
    @Provides
    fun provideImageRemoteApi(): ImageRemoteApi {
        return ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStorageDatabase(context: Context): StorageDatabase =
        Room.databaseBuilder(
            context,
            StorageDatabase::class.java,
            STORAGE_DB
        ).addMigrations(object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE logo ADD COLUMN thumbImage BLOB DEFAULT NULL")
            }
        }).build()

    @Provides
    fun provideTournamentDao(database: StorageDatabase): TournamentDao =
        database.tournamentDao()

    @Provides
    fun provideLogoDao(database: StorageDatabase): LogoDao =
        database.logoDao()
}
