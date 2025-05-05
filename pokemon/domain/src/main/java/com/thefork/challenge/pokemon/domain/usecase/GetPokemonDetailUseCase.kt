package com.thefork.challenge.pokemon.domain.usecase

import com.thefork.challenge.pokemon.domain.entity.Pokemon
import com.thefork.challenge.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    suspend fun invoke(id: Int): Pokemon? = withContext(Dispatchers.IO) {
        repository.getPokemonDetails(id)
    }
}