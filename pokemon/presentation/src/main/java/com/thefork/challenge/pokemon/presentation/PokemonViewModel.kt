package com.thefork.challenge.pokemon.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thefork.challenge.pokemon.domain.entity.Pokemon
import com.thefork.challenge.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState.StatState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonScreenState>(PokemonScreenState.DataLoading)
    val uiState: StateFlow<PokemonScreenState> = _uiState

    fun getPokemonScreenState(id: Int) {
        _uiState.value = PokemonScreenState.DataLoading
        viewModelScope.launch {
            val pokemon = getPokemonDetailUseCase.invoke(id)
            if (pokemon == null) {
                _uiState.value = PokemonScreenState.DataNotAvailable
            } else {
                _uiState.value = PokemonScreenState.DataAvailable(
                    name = pokemon.name,
                    PokemonContentState(
                        types = pokemon.types.joinToString(separator = ", "),
                        speciesColor = determineSpeciesColor(pokemon.speciesColor),
                        logoUrl = pokemon.logoUrl,
                        height = pokemon.height,
                        weight = pokemon.weight,
                        baseStats = pokemon.stats.map { StatState(it.name, it.value) }
                    )
                )
            }
        }
    }

    private fun determineSpeciesColor(color: Pokemon.SpeciesColor?) =
        if (color != null) Color(color.argb) else Color.Red
}