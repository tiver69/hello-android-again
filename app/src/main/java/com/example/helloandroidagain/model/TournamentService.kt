package com.example.helloandroidagain.model

import java.time.LocalDate
import kotlin.random.Random

interface TournamentListListener {
    fun tournamentListUpdated(tournamentList: List<Tournament>)
}

class TournamentService(val tournamentListListener: TournamentListListener) {

    private var tournaments = mutableListOf<Tournament>()
    private var tmpIdGenerator: Long = 20
        get() {
            return ++field
        }

    init {
        tournaments = (0..19).map {
            val randomDate = if (Random.nextInt(0, 5) >= 2)
                LocalDate.now().plusDays(Random.nextInt(1, 5).toLong())
            else
                LocalDate.now().minusDays(Random.nextInt(1, 5).toLong())
            Tournament(
                it.toLong(), "Tournament$it", Random.nextInt(2, 10), randomDate
            )
        }.toMutableList()
    }

    fun getTournaments(): List<Tournament> {
        return tournaments
    }

    fun addTournament(tournament: Tournament) {
        tournaments = tournaments.toMutableList()
        tournaments.add(
            Tournament(
                tmpIdGenerator,
                tournament.name,
                tournament.participantCount,
                tournament.date
            )
        )

        tournamentListListener.tournamentListUpdated(tournaments)
    }

    fun removeTournament(tournament: Tournament) {
        removeTournament(tournament.id)
    }

    fun removeTournament(id: Long) {
        tournaments = tournaments.toMutableList()
        tournaments.removeIf { it.id == id }
        tournamentListListener.tournamentListUpdated(tournaments)
    }
}