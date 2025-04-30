package com.thefork.challenge.pokemon.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.thefork.challenge.pokemon.presentation.component.PokemonContent
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState.StatState
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier
) {
    val state by remember {
        mutableStateOf(
            PokemonContentState(
                name = "Mew",
                speciesName = "Psychic",
                speciesColor = Color.Red,
                logoUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                height = 4,
                weight = 4,
                baseStats = listOf(
                    StatState("Hp", 100),
                    StatState("Attack", 100),
                    StatState("Defence", 100),
                    StatState("Special-attack", 100),
                    StatState("Special-defence", 100),
                    StatState("Speed", 100),
                )
            )
        )
    }

    Scaffold(
        topBar = { PokemonTopBar(state.name, state.speciesColor) },
    ) { innerPadding ->
        PokemonContent(
            pokemonState = state,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonTopBar(
    name: String,
    speciesColor: Color,
) {
    CenterAlignedTopAppBar(
        title = { Text(name) },
        navigationIcon = {
            IconButton(onClick = {
                // todo: back nav handler
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "top menu"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            .copy(containerColor = speciesColor),
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonTopBarPreview() {
    PokemonTheme {
        PokemonTopBar("Mew", Color.Red)
    }
}