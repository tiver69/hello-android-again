package com.example.helloandroidagain.navigation

import androidx.fragment.app.Fragment

fun Fragment.router(): Router {
    return requireActivity() as Router
}

interface Router {
    fun navToCreateTournament(nextTournamentCount: Int)
    fun navToTournamentList()
}