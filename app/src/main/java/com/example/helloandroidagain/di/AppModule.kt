package com.example.helloandroidagain.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private const val TOURNAMENT_LIST_PREF = "tournament_list_pref"
    }

    @Provides
    @Singleton
    fun getAppContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun getAppSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(TOURNAMENT_LIST_PREF, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun getFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
}