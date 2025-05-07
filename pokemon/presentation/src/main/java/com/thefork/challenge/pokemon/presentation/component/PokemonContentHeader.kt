package com.thefork.challenge.pokemon.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonContentHeader(
    speciesColor: Color,
    modifier: Modifier = Modifier,
    additionalContent: @Composable BoxScope.() -> Unit = {},
    imageContent: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
            .background(speciesColor),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            modifier = Modifier
                .height(20.dp)
                .background(Color.Black)
        )
        PokemonHeaderPicture(imageContent)
        additionalContent()
    }
}

@Composable
private fun PokemonHeaderPicture(
    imageContent: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.White, shape = CircleShape)
            .border(10.dp, Color.Black, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        imageContent()
    }
}

@Preview(showBackground = true)
@Composable
fun SimplePokemonContentHeaderPreview() {
    PokemonTheme {
        PokemonContentHeader(
            speciesColor = Color.Red,
        ) { }
    }
}