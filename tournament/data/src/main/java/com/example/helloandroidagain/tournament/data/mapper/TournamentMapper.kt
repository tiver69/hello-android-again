package com.example.helloandroidagain.tournament.data.mapper

import com.example.helloandroidagain.tournament.data.db.entity.TournamentEntity
import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.model.TournamentLogo

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