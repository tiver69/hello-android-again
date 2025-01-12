package com.example.helloandroidagain.di

import android.content.Context
import android.content.SharedPreferences
import com.example.helloandroidagain.data.db.ImageCacheDatabaseHelper
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

    @Singleton
    @Provides
    fun provideTournamentRepository(sharedPreferences: SharedPreferences): TournamentRepository {
        return TournamentRepositoryImpl(sharedPreferences)
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
}