package com.example.helloandroidagain.tournament.data.mapper

import com.example.helloandroidagain.tournament.data.db.entity.LogoEntity
import com.example.helloandroidagain.tournament.domain.model.TournamentLogo
import com.google.gson.JsonObject

class LogoMapper {

    companion object {
        const val JSON_RESULT_PATH = "results"
        private const val JSON_URL_PATH = "urls"

        fun mapJsonObjectToDomain(jsonLogo: JsonObject): TournamentLogo =
            TournamentLogo(
                jsonLogo.get("id").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("raw").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("regular").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("thumb").asString
            )

        fun toData(tournamentLogo: TournamentLogo): LogoEntity = with(tournamentLogo) {
            LogoEntity(
                id = id,
                rawUrl = rawUrl,
                regularUrl = regularUrl,
                thumbUrl = thumbUrl,
                thumbImage = null
            )
        }

        fun toDomain(logoEntity: LogoEntity): TournamentLogo = with(logoEntity) {
            TournamentLogo(
                id = id,
                rawUrl = rawUrl,
                regularUrl = regularUrl,
                thumbUrl = thumbUrl
            )
        }
    }
}