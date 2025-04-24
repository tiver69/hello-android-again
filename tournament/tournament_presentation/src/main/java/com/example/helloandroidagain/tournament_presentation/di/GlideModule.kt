package com.example.helloandroidagain.tournament_presentation.di

import android.content.Context
import com.example.helloandroidagain.tournament_domain.repository.ImageCacheRepository
import com.example.helloandroidagain.tournament_presentation.component.glide.CustomCacheLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GlideModule {

    @Provides
    @Singleton
    fun provideCustomCacheLoader(
        context: Context,
        imageCacheRepository: ImageCacheRepository
    ): CustomCacheLoader =
        CustomCacheLoader(context, imageCacheRepository)
}