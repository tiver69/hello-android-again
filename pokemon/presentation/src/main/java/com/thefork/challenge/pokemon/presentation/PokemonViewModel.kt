package com.thefork.challenge.pokemon.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thefork.challenge.pokemon.data.repository.PokemonRepositoryImpl
import com.thefork.challenge.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState.StatState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonScreenState(false, null))
    val uiState: StateFlow<PokemonScreenState> = _uiState

    private val getPokemonDetailUseCase: GetPokemonDetailUseCase =
        GetPokemonDetailUseCase(PokemonRepositoryImpl())

    fun getPokemonScreenState(id: Int) {
        viewModelScope.launch {
            val pokemon = getPokemonDetailUseCase.invoke(id)
            if (pokemon == null) {
                _uiState.value = PokemonScreenState(false, null)
            } else
                _uiState.value = PokemonScreenState(
                    data =
                        PokemonContentState(
                            name = pokemon.name,
                            types = pokemon.types.joinToString(separator = ", "),
                            speciesColor = Color(pokemon.speciesColor!!.argb),
                            logoUrl = pokemon.logoUrl,
                            height = pokemon.height,
                            weight = pokemon.weight,
                            baseStats = pokemon.stats.map { StatState(it.name, it.value) }
                        )
                )
        }
    }
}