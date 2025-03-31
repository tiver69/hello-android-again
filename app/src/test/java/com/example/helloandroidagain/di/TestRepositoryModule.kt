package com.example.helloandroidagain.di

import com.example.helloandroidagain.data.repository.remote.ImageRemoteApi
import com.example.helloandroidagain.data.repository.remote.ImageRetrofitInstance
import com.example.helloandroidagain.domain.repository.ExportRepository
import com.example.helloandroidagain.domain.repository.ImageCacheRepository
import com.example.helloandroidagain.domain.repository.TournamentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fakes.FakeExportRepositoryImpl
import fakes.FakeImageCacheRepositoryImpl
import fakes.FakeTournamentRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestRepositoryModule {

    @Singleton
    @Provides
    fun provideImageCacheRepository(): ImageCacheRepository = FakeImageCacheRepositoryImpl()

    @Singleton
    @Provides
    fun provideTournamentRepository(): TournamentRepository = FakeTournamentRepositoryImpl()

    @Singleton
    @Provides
    fun provideExportRepository(): ExportRepository = FakeExportRepositoryImpl()

    @Singleton
    @Provides
    fun provideImageRemoteApi(): ImageRemoteApi {
        return ImageRetrofitInstance.retrofit.create(ImageRemoteApi::class.java)
    }
}
