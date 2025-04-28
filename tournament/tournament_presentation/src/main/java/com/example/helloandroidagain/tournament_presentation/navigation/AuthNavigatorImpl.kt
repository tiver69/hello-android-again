package com.example.helloandroidagain.tournament_presentation.navigation

import android.content.Context
import android.content.Intent
import com.example.helloandroidagain.core.navigation.AuthNavigator
import com.example.helloandroidagain.tournament_presentation.TournamentActivity
import javax.inject.Inject

class AuthNavigatorImpl @Inject constructor() : AuthNavigator {
    override fun openTournamentActivity(context: Context) {
        val intent = Intent(context, TournamentActivity::class.java)
        context.startActivity(intent)
    }
}