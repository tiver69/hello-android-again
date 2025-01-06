package com.example.helloandroidagain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Tournament(
    val id: Long,
    val name: String,
    val participantCount: Int,
    val date: LocalDate,
    val logo: TournamentLogo,
) : Parcelable
