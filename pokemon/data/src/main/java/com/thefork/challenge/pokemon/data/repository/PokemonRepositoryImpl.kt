package com.thefork.challenge.pokemon.data.repository

import com.thefork.challenge.api.Api
import com.thefork.challenge.api.PokemonResponse
import com.thefork.challenge.api.PokemonService
import com.thefork.challenge.pokemon.data.mapper.PokemonMapper
import com.thefork.challenge.pokemon.domain.entity.Pokemon
import com.thefork.challenge.pokemon.domain.repository.PokemonRepository

class PokemonRepositoryImpl : PokemonRepository {

    private val pokemonService: PokemonService = Api().pokemonService

    override suspend fun getPokemonDetails(id: Int): Pokemon? {
        val response = pokemonService.getPokemon(id.toString())
        return if (response.isSuccessful && response.body() != null) {
            val speciesColor = getPokemonColor(response.body()!!.species!!.url) //todo
            val pokemon = PokemonMapper().mapToEntity(
                response.body() as PokemonResponse,
                speciesColor
            )
            pokemon
        } else null
    }

    private suspend fun getPokemonColor(speciesUrl: String): String? {
        val speciesId = speciesUrl.split("/".toRegex()).dropLast(1).last()
        val colorResponse = pokemonService.getPokemonSpecies(speciesId)
        return if (colorResponse.isSuccessful) colorResponse.body()!!.color.name
        else null
    }
}
