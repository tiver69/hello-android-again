package com.example.helloandroidagain.data.model

import android.os.Parcelable
import com.example.helloandroidagain.data.db.entity.LogoEntity
import com.example.helloandroidagain.data.db.entity.TournamentEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Tournament(
    val id: Long,
    val name: String,
    val participantCount: Int,
    val date: LocalDate,
    val logo: TournamentLogo,
) : Parcelable {

    fun toNewEntity(): TournamentEntity = TournamentEntity(
        name = name,
        participantCount = participantCount,
        date = date,
        logoId = logo.id
    )

    fun toEntity(): TournamentEntity = TournamentEntity(id, name, participantCount, date, logo.id)
    fun toLogoEntity(): LogoEntity =
        LogoEntity(logo.id, logo.rawUrl, logo.regularUrl, logo.thumbUrl)
}
