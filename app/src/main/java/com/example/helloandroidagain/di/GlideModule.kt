package com.example.helloandroidagain.di

import android.content.Context
import com.example.helloandroidagain.data.repository.local.ImageCacheRepository
import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader
import dagger.Module
import dagger.Provides

@Module
class GlideModule {

    @Provides
    fun provideCustomCacheLoader(
        context: Context,
        imageCacheRepository: ImageCacheRepository
    ): CustomCacheLoader =
        CustomCacheLoader(context, imageCacheRepository)
}