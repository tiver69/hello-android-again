package com.example.helloandroidagain.model

import android.content.Context
import android.content.SharedPreferences
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
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.lang.reflect.Type
import java.time.LocalDate
import kotlin.random.Random

class TournamentService(
    context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(TOURNAMENT_LIST_PREF, Context.MODE_PRIVATE)
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
    private val tournamentsSubject: BehaviorSubject<List<Tournament>> =
        BehaviorSubject.createDefault(restoreTournaments())
    private var tmpIdGenerator: Long = 20

    fun saveTournaments() {
        val json = gson.toJson(tournamentsSubject.value)
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

    fun getTournaments(): Observable<List<Tournament>> {
        return tournamentsSubject.hide()
    }

    fun addTournament(tournament: Tournament) {
        val updatedTournaments = tournamentsSubject.value.orEmpty().toMutableList().apply {
            add(
                Tournament(
                    ++tmpIdGenerator,
                    tournament.name,
                    tournament.participantCount,
                    tournament.date,
                    tournament.logo
                )
            )
        }
        tournamentsSubject.onNext(updatedTournaments.toList())
    }

    fun removeTournament(tournamentPosition: Int) {
        val updatedTournaments = tournamentsSubject.value.orEmpty().toMutableList().apply {
            removeAt(tournamentPosition)
        }
        tournamentsSubject.onNext(updatedTournaments.toList())
    }

    companion object {
        private const val TOURNAMENT_LIST_PREF = "tournament_list_pref"
        private const val TOURNAMENT_LIST = "tournament_list"
        private const val TMP_ID_GENERATOR = "tmp_id_generator"
    }
}