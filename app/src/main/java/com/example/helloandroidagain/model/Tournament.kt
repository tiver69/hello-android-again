package com.example.helloandroidagain.model

import java.time.LocalDate

data class Tournament(
    val id: Long,
    val name: String,
    val participantCount: Int,
    val date: LocalDate
)
