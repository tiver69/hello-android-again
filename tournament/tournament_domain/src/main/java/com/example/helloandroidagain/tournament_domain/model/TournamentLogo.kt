package com.example.helloandroidagain.tournament_domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TournamentLogo(
    val id: String,
    val rawUrl: String,
    val regularUrl: String,
    val thumbUrl: String,
) : Parcelable {

    companion object {

        fun default(): TournamentLogo = TournamentLogo(
            id = "aKh09-sEWF8",
            rawUrl = "https://images.unsplash.com/photo-1524951525241-4144ce9de04b?ixid=M3w2ODc1NzZ8MHwxfGNvbGxlY3Rpb258MXxST3BKUDMxQ3BSY3x8fHx8Mnx8MTczNDU2OTczNnw&ixlib=rb-4.0.3",
            regularUrl = "https://images.unsplash.com/photo-1524951525241-4144ce9de04b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2ODc1NzZ8MHwxfGNvbGxlY3Rpb258MXxST3BKUDMxQ3BSY3x8fHx8Mnx8MTczNDU2OTczNnw&ixlib=rb-4.0.3&q=80&w=1080",
            thumbUrl = "https://images.unsplash.com/photo-1524951525241-4144ce9de04b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2ODc1NzZ8MHwxfGNvbGxlY3Rpb258MXxST3BKUDMxQ3BSY3x8fHx8Mnx8MTczNDU2OTczNnw&ixlib=rb-4.0.3&q=80&w=200"
        )
    }
}