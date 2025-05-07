package com.thefork.challenge.pokemon.data.mapper

import com.thefork.challenge.common.api.model.PokemonResponse
import com.thefork.challenge.pokemon.domain.entity.Pokemon

class PokemonMapper {

    fun mapToEntity(response: PokemonResponse, colorName: String?) = with(response) {
        Pokemon(
            id = id,
            name = name.replaceFirstChar(Char::titlecase),
            logoUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
            speciesColor = Pokemon.SpeciesColor.fromNaming(colorName!!),
            height = height / 10f,
            weight = weight,
            types = types.map { it.type.name.replaceFirstChar(Char::titlecase) }.toList(),
            stats = stats.map {
                Pokemon.Stat(
                    it.stat.name.replaceFirstChar(Char::titlecase),
                    it.base_stat
                )
            }
        )
    }
}