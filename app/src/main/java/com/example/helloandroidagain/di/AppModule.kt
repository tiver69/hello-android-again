package com.example.helloandroidagain.di

import android.app.Application
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideFusedLocationProvider(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

}