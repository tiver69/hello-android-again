package com.example.helloandroidagain.di

import com.example.helloandroidagain.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

    @Provides
    @Named("unsplash_base_url")
    fun provideUnsplashBaseUrl(): String = BuildConfig.UNSPLASH_BASE_URL

    @Provides
    @Named("unsplash_client_id")
    fun provideUnsplashClientId(): String = BuildConfig.UNSPLASH_CLIENT_ID
}