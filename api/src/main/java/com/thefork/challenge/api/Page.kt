package com.thefork.challenge.api

class Page<T>(
    val results: List<T>,
    val count: UInt,
    val previous: String?,
    val next: String?,
)
