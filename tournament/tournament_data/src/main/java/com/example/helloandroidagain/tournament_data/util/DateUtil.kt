package com.example.helloandroidagain.tournament_data.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "dd.MM.yyyy"

fun String.convertToLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT))

fun LocalDate.convertToString(): String = this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))