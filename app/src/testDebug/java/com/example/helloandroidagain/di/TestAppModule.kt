package com.example.helloandroidagain.di

import android.app.Application
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.mockk
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun getAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun getFirebaseAnalytics(): FirebaseAnalytics = mockk(relaxed = true)
}