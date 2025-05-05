package com.thefork.challenge.api

data class PokemonResponse(
    val id: Long,
    val name: String,
    val height: Int,
    val weight: Int,
    val species: Species,
    val types: List<TypeContainer>,
    val stats: List<StatContainer>,
) {
    data class TypeContainer(
        val type: Type,
    )

    data class Type(
        val name: String,
    )

    data class StatContainer(
        val stat: Stat,
        val base_stat: Int
    )

    data class Stat(
        val name: String,
    )

    data class Species(
        val url: String
    )
}
