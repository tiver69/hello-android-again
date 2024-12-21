package com.example.helloandroidagain.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.helloandroidagain.model.Tournament

fun Fragment.router(): Router {
    return requireActivity() as Router
}

interface Router {
    fun navToCreateTournament(nextTournamentCount: Int)
    fun navToTournamentList()
    fun navBack()
    fun createResult(tournament: Parcelable)
    fun listenToCreateResult(owner: LifecycleOwner, listener: CreateTournamentResultListener)
}

interface CreateTournamentResultListener {
    fun tournaemntCreated(tournament: Tournament)
}