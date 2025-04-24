package com.example.helloandroidagain.di

import com.example.helloandroidagain.di.fakes.FakeExportRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeImageCacheRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeImageRemoteRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeTournamentRepositoryImpl
import com.example.helloandroidagain.tournament_domain.repository.ExportRepository
import com.example.helloandroidagain.tournament_domain.repository.ImageCacheRepository
import com.example.helloandroidagain.tournament_domain.repository.ImageRemoteRepository
import com.example.helloandroidagain.tournament_domain.repository.TournamentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestTournamentDataModule {

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
    fun provideImageRemoteRepository(): ImageRemoteRepository = FakeImageRemoteRepositoryImpl()
}
