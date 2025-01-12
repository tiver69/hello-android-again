package com.example.helloandroidagain.di

import android.content.Context
import com.example.helloandroidagain.data.repository.local.ImageCacheRepository
import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader
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