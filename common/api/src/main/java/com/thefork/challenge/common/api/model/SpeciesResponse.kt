package com.thefork.challenge.common.api.model

data class SpeciesResponse(
    val color: ColorContainer
) {
    data class ColorContainer(
        val name: String
    )
}
