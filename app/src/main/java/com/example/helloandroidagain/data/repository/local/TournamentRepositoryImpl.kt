package com.example.helloandroidagain.data.repository.local

import android.content.SharedPreferences
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.domain.repository.TournamentRepository
import com.example.helloandroidagain.util.convertToLocalDate
import com.example.helloandroidagain.util.convertToString
import com.example.helloandroidagain.util.generateRandomDate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.reflect.Type
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

class TournamentRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    TournamentRepository {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, object : JsonSerializer<LocalDate> {
            override fun serialize(
                src: LocalDate?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement = JsonPrimitive(src?.convertToString())
        })
        .registerTypeAdapter(LocalDate::class.java, object : JsonDeserializer<LocalDate> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDate = json?.asString?.convertToLocalDate()!!
        })
        .create()

    private val _tournamentsFlow: MutableStateFlow<List<Tournament>> =
        MutableStateFlow(restoreTournaments())
    private var tmpIdGenerator: Long = 20

    override fun saveTournaments() {
        val json = gson.toJson(_tournamentsFlow.value)
        sharedPreferences.edit()
            .putString(TOURNAMENT_LIST, json)
            .putLong(TMP_ID_GENERATOR, tmpIdGenerator)
            .apply()
    }

    private fun restoreTournaments(): List<Tournament> {
        tmpIdGenerator = sharedPreferences.getLong(TMP_ID_GENERATOR, 20)
        val json = sharedPreferences.getString(TOURNAMENT_LIST, null)
        return if (json != null) gson.fromJson(
            json, object : TypeToken<List<Tournament>>() {}.type
        ) else emptyList()
    }

    private fun generateTournaments(): List<Tournament> = (0..<tmpIdGenerator).map {
        Tournament(
            it,
            "Tournament$it",
            Random.nextInt(2, 10),
            generateRandomDate(),
            TournamentLogo.default()
        )
    }.toMutableList()

    override fun getTournaments(): StateFlow<List<Tournament>> = _tournamentsFlow.asStateFlow()

    override fun addTournament(tournament: Tournament) {
        val updatedTournamentsFlow = _tournamentsFlow.value + Tournament(
            ++tmpIdGenerator,
            tournament.name,
            tournament.participantCount,
            tournament.date,
            tournament.logo
        )
        _tournamentsFlow.value = updatedTournamentsFlow
    }

    override fun removeTournament(tournamentPosition: Int) {
        val updatedTournamentsFlow = _tournamentsFlow.value.filterIndexed { index, _ ->
            index != tournamentPosition
        }
        _tournamentsFlow.value = updatedTournamentsFlow
    }

    companion object {
        private const val TOURNAMENT_LIST = "tournament_list"
        private const val TMP_ID_GENERATOR = "tmp_id_generator"
    }
}