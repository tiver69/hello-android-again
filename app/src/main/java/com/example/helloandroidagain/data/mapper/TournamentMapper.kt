package com.example.helloandroidagain.data.mapper

import com.example.helloandroidagain.data.db.entity.TournamentEntity
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo

class TournamentMapper {

    companion object {
        fun toData(tournament: Tournament): TournamentEntity = with(tournament) {
            TournamentEntity(
                id = id,
                name = name,
                participantCount = participantCount,
                date = date,
                logoId = logo.id
            )
        }

        fun toDomain(tournament: TournamentEntity, logo: TournamentLogo): Tournament =
            with(tournament) {
                Tournament(
                    id = id,
                    name = name,
                    participantCount = participantCount,
                    date = date,
                    logo = logo
                )
            }
    }
}