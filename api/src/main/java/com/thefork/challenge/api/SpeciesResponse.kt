package com.thefork.challenge.api

data class SpeciesResponse(
    val color: ColorContainer
) {
    data class ColorContainer(
        val name: String
    )
}
