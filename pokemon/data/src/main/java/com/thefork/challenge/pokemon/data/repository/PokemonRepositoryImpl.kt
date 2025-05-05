package com.thefork.challenge.pokemon.data.repository

import com.thefork.challenge.api.Api
import com.thefork.challenge.api.PokemonResponse
import com.thefork.challenge.api.PokemonService
import com.thefork.challenge.pokemon.data.mapper.PokemonMapper
import com.thefork.challenge.pokemon.domain.entity.Pokemon
import com.thefork.challenge.pokemon.domain.repository.PokemonRepository

class PokemonRepositoryImpl : PokemonRepository {

    private val pokemonService: PokemonService = Api().pokemonService

    override suspend fun getPokemonDetails(id: Int): Pokemon? = try {
        val response = pokemonService.getPokemon(id.toString())
        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                val speciesColor = getPokemonColor(responseBody.species.url)
                PokemonMapper().mapToEntity(
                    response.body() as PokemonResponse,
                    speciesColor
                )
            }
        } else null
    } catch (e: Exception) {
        null
    }

    private suspend fun getPokemonColor(speciesUrl: String): String? {
        val speciesId = speciesUrl.split("/".toRegex()).dropLast(1).last()
        val colorResponse = pokemonService.getPokemonSpecies(speciesId)
        return if (colorResponse.isSuccessful)
            colorResponse.body()?.color?.name
        else null
    }
}
