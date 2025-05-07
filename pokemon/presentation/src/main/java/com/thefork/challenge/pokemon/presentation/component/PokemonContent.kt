package com.thefork.challenge.pokemon.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.thefork.challenge.pokemon.presentation.R
import com.thefork.challenge.pokemon.presentation.component.PokemonContentState.StatState
import com.thefork.challenge.pokemon.presentation.theme.LocalSpacing
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonContent(
    pokemonState: PokemonContentState,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        PokemonContentHeader(
            pokemonState.types,
            pokemonState.speciesColor,
            pokemonState.logoUrl
        )
        PokemonContentBody(
            pokemonState.height,
            pokemonState.weight,
            pokemonState.baseStats
        )
    }
}

@Composable
private fun PokemonContentHeader(
    types: String,
    speciesColor: Color,
    logoUrl: String,
) {
    PokemonContentHeader(
        speciesColor = speciesColor,
        additionalContent = {
            BackgroundAwareText(
                text = types,
                backgroundColor = speciesColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(LocalSpacing.current.spaceSmall)
                    .align(Alignment.BottomCenter)
            )
        }
    ) {
        AsyncImage(
            model = logoUrl,
            contentDescription = null,
            error = rememberAsyncImagePainter(R.drawable.pokemon_logo_placeholder),
            placeholder = rememberAsyncImagePainter(R.drawable.pokemon_logo_placeholder),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(0.75f),
        )
    }
}

@Composable
private fun PokemonContentBody(
    height: Float,
    weight: Int,
    baseStats: List<StatState>
) {
    Column(
        modifier = Modifier.padding(horizontal = LocalSpacing.current.spaceSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.spaceMedium),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Height: $height m",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
            )
            Text(
                text = "Weight: $weight Kg",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        PokemonStatsColumn(baseStats)
    }
}

@Composable
private fun PokemonStatsColumn(
    stats: List<StatState>
) {
    LazyColumn {
        item {
            Text(
                text = "Base Stats",
                style = MaterialTheme.typography.titleMedium
            )
            HorizontalDivider()
        }
        items(stats) { stat ->
            PokemonStatsItem(stat)
        }
    }
}

@Composable
fun PokemonStatsItem(stat: StatState) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val spacing = LocalSpacing.current
        Text(
            text = "${stat.name}:",
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = spacing.spaceSmall)
        )
        Text(
            text = stat.value.toString(),
            modifier = Modifier
                .padding(vertical = spacing.spaceSmall)
                .padding(start = spacing.spaceExtraSmall),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

data class PokemonContentState(
    val types: String,
    val speciesColor: Color,
    val logoUrl: String,
    val height: Float,
    val weight: Int,
    val baseStats: List<StatState>
) {
    data class StatState(
        val name: String,
        val value: Int
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonContentPreview() {
    PokemonTheme {
        PokemonContent(
            PokemonContentState(
                types = "Psychic",
                speciesColor = Color.Red,
                logoUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                height = 0.4f,
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
}