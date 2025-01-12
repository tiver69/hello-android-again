package com.example.helloandroidagain.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    companion object {
        private const val TOURNAMENT_LIST_PREF = "tournament_list_pref"
    }

    @Provides
    fun getAppContext(): Context = context

    @Provides
    fun getAppSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(TOURNAMENT_LIST_PREF, Context.MODE_PRIVATE)
}