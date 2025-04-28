package com.example.helloandroidagain.di

import com.example.helloandroidagain.di.fakes.FakeExportRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeImageCacheRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeImageRemoteRepositoryImpl
import com.example.helloandroidagain.di.fakes.FakeTournamentRepositoryImpl
import com.example.helloandroidagain.tournament.domain.repository.ExportRepository
import com.example.helloandroidagain.tournament.domain.repository.ImageCacheRepository
import com.example.helloandroidagain.tournament.domain.repository.ImageRemoteRepository
import com.example.helloandroidagain.tournament.domain.repository.TournamentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TestTournamentDataModule {

    @Singleton
    @Binds
    fun provideImageCacheRepository(impl: FakeImageCacheRepositoryImpl): ImageCacheRepository

    @Singleton
    @Binds
    fun provideTournamentRepository(impl: FakeTournamentRepositoryImpl): TournamentRepository

    @Singleton
    @Binds
    fun provideExportRepository(impl: FakeExportRepositoryImpl): ExportRepository

    @Singleton
    @Binds
    fun provideImageRemoteRepository(impl: FakeImageRemoteRepositoryImpl): ImageRemoteRepository
}
