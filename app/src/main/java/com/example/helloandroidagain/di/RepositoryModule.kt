package com.example.helloandroidagain.di

import android.content.Context
import androidx.room.Room
import com.example.helloandroidagain.data.db.ImageCacheDatabaseHelper
import com.example.helloandroidagain.data.db.LogoDao
import com.example.helloandroidagain.data.db.TournamentDao
import com.example.helloandroidagain.data.db.StorageDatabase
import com.example.helloandroidagain.data.repository.local.TournamentRepositoryImpl
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.data.repository.remote.ImageRetrofitInstance
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
    fun provideTournamentRepository(
        tournamentDao: TournamentDao,
        logoDao: LogoDao
    ): TournamentRepository {
        return TournamentRepositoryImpl(tournamentDao, logoDao)
    }

    @Singleton
    @Provides
    fun provideImageRemoteApi(): ImageRemoteApi {
        return ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImageCacheDatabaseHelper(context: Context): ImageCacheDatabaseHelper =
        ImageCacheDatabaseHelper(context)

    @Provides
    @Singleton
    fun provideStorageDatabase(context: Context): StorageDatabase =
        Room.databaseBuilder(
            context,
            StorageDatabase::class.java,
            STORAGE_DB
        ).build()

    @Provides
    fun provideTournamentDao(database: StorageDatabase): TournamentDao =
        database.tournamentDao()

    @Provides
    fun provideLogoDao(database: StorageDatabase): LogoDao =
        database.logoDao()
}
