package com.example.helloandroidagain.di

import android.content.SharedPreferences
import com.example.helloandroidagain.data.repository.local.TournamentRepositoryImpl
import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.data.repository.remote.ImageRetrofitInstance
import com.example.helloandroidagain.domain.repository.TournamentRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @AppScope
    @Provides
    fun provideTournamentRepository(sharedPreferences: SharedPreferences): TournamentRepository {
        return TournamentRepositoryImpl(sharedPreferences)
    }

    @AppScope
    @Provides
    fun provideImageRemoteApi(): ImageRemoteApi {
        return ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java)
    }
}