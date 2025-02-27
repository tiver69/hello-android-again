package com.example.helloandroidagain.data.mapper

import com.example.helloandroidagain.data.db.entity.LogoEntity
import com.example.helloandroidagain.data.db.entity.TournamentEntity
import com.example.helloandroidagain.data.model.Tournament

fun Tournament.toNewEntity() = TournamentEntity(
    name = name,
    participantCount = participantCount,
    date = date,
    logoId = logo.id
)

fun Tournament.toLogoEntity() = LogoEntity(
    logo.id,
    logo.rawUrl,
    logo.regularUrl,
    logo.thumbUrl
)
