package com.example.helloandroidagain.data.mapper

import com.example.helloandroidagain.data.model.TournamentLogo
import com.google.gson.JsonObject

class TournamentLogoMapper {

    companion object {
        const val JSON_RESULT_PATH = "results"
        private const val JSON_URL_PATH = "urls"

        fun mapJsonObjectToTournamentLogo(jsonLogo: JsonObject): TournamentLogo =
            TournamentLogo(
                jsonLogo.get("id").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("raw").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("regular").asString,
                jsonLogo.getAsJsonObject(JSON_URL_PATH).get("thumb").asString
            )
    }
}