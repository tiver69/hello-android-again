package com.thefork.challenge.common.api.model

class Page<T>(
    val results: List<T>,
    val count: UInt,
    val previous: String?,
    val next: String?,
)
