package com.thefork.challenge.pokemon.domain.repository

import com.thefork.challenge.pokemon.domain.entity.Pokemon


interface PokemonRepository {
    suspend fun getPokemonDetails(id: Int) : Pokemon?
}