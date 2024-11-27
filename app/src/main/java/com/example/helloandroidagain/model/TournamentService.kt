package com.example.helloandroidagain.model

import java.time.LocalDate
import kotlin.random.Random

class TournamentService {

    private var tournaments = mutableListOf<Tournament>()

    init {
        tournaments = (0..19).map {
            Tournament(
                it.toLong(), "Tournament$it", Random.nextInt(2, 10), LocalDate.now()
            )
        }.toMutableList()
    }

    fun getTournaments(): List<Tournament> {
        return tournaments
    }

    fun addTournament(tournament: Tournament) {
        tournaments.add(tournament)
    }

    fun removeTournament(tournament: Tournament) {
        removeTournament(tournament.id)
    }

    fun removeTournament(id: Long) {
        tournaments.removeIf { it.id == id }
    }
}