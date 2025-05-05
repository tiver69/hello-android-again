package com.thefork.challenge.pokemon.presentation

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
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: PokemonViewModel = viewModel()
    LaunchedEffect(17) {
        viewModel.getPokemonScreenState(19)
    }
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isDataAvailable)
        Scaffold(
            topBar = { PokemonTopBar(uiState.data!!.name, uiState.data!!.speciesColor) },//todo
        ) { innerPadding ->
            PokemonContent(
                pokemonState = uiState.data!!,//todo
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
        title = { BackgroundAwareText(name, speciesColor) },
        navigationIcon = {
            IconButton(onClick = {
                // todo: back nav handler
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

data class PokemonScreenState(
    val isDataAvailable: Boolean = true,
    val data: PokemonContentState?
)

@Preview(showBackground = true)
@Composable
fun PokemonTopBarPreview() {
    PokemonTheme {
        PokemonTopBar("Mew", Color.Red)
    }
}