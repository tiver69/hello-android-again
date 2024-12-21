package com.example.helloandroidagain.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

const val DATE_FORMAT = "dd.MM.yyyy"

fun String.convertToLocalDate(): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(DATE_FORMAT))

fun LocalDate.convertToString(): String = this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

fun Long.convertToLocalDateAsEpochMilli(): LocalDate = Instant.ofEpochMilli(this)
    .atZone(ZoneId.systemDefault())
    .toLocalDate()

fun LocalDate.convertToLongAsEpochMilli(): Long =
    LocalDateTime.of(this, LocalTime.NOON).atZone(ZoneOffset.UTC).toInstant().toEpochMilli()


fun generateRandomDate(): LocalDate =
    if (Random.nextInt(0, 5) >= 2)
        LocalDate.now().plusDays(Random.nextInt(1, 5).toLong())
    else
        LocalDate.now().minusDays(Random.nextInt(1, 5).toLong())