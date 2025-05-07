package com.thefork.challenge.pokemon.presentation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thefork.challenge.pokemon.presentation.component.BackgroundAwareText
import com.thefork.challenge.pokemon.presentation.component.PokemonContent
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState
import com.thefork.challenge.pokemon.presentation.component.PokemonErrorContent
import com.thefork.challenge.pokemon.presentation.component.PokemonLoadingContent
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonScreen(
    pokemonId: String,
    modifier: Modifier = Modifier
) {
    val viewModel: PokemonViewModel = viewModel()
    LaunchedEffect(pokemonId) {
        viewModel.getPokemonScreenState(pokemonId)
    }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { PokemonTopBar(uiState.topBarTitle, uiState.topBarColor) },
    ) { innerPadding ->
        when (uiState) {
            is PokemonScreenState.DataLoading -> PokemonLoadingContent(
                modifier = modifier.padding(innerPadding)
            )

            is PokemonScreenState.DataNotAvailable -> {
                PokemonErrorContent(
                    modifier = modifier.padding(innerPadding)
                ) {
                    viewModel.getPokemonScreenState(pokemonId)
                }
            }

            is PokemonScreenState.DataAvailable -> {
                val pokemonData = (uiState as PokemonScreenState.DataAvailable).pokemonContentState
                PokemonContent(
                    pokemonState = pokemonData,
                    modifier = modifier.padding(innerPadding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonTopBar(
    name: String,
    speciesColor: Color,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    CenterAlignedTopAppBar(
        title = { BackgroundAwareText(name, speciesColor) },
        navigationIcon = {
            IconButton(
                onClick = {
                    backDispatcher?.onBackPressed()
                }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "top menu",
                    tint = if (speciesColor.luminance() > 0.2) Color.Black else Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            .copy(containerColor = speciesColor),
    )
}

sealed class PokemonScreenState(val topBarTitle: String, val topBarColor: Color = Color.Red) {
    data object DataLoading : PokemonScreenState("Loading")
    data object DataNotAvailable : PokemonScreenState("")
    data class DataAvailable(val name: String, val pokemonContentState: PokemonContentState) :
        PokemonScreenState(name, pokemonContentState.speciesColor)
}

@Preview(showBackground = true)
@Composable
fun PokemonTopBarPreview() {
    PokemonTheme {
        PokemonTopBar("Mew", Color.Red)
    }
}