package com.example.helloandroidagain.tournament_data.mapper

import com.example.helloandroidagain.tournament_data.mapper.LogoMapper.Companion.JSON_RESULT_PATH
import com.example.helloandroidagain.tournament_data.mapper.LogoMapper.Companion.mapJsonObjectToDomain
import com.example.helloandroidagain.tournament_domain.model.TournamentLogo
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Converter

class RemoteImageResponseConverter(private val gson: Gson) :
    Converter<ResponseBody, List<TournamentLogo>> {
    override fun convert(value: ResponseBody): List<TournamentLogo> {
        val jsonObject = gson.fromJson(value.string(), JsonObject::class.java)
        val result = mutableListOf<TournamentLogo>()
        jsonObject.getAsJsonArray(JSON_RESULT_PATH).forEach { jsonLogo ->
            result.add(mapJsonObjectToDomain(jsonLogo.asJsonObject))
        }
        return result.toList()
    }
}