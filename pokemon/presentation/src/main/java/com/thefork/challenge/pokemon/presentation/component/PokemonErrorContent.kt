package com.thefork.challenge.pokemon.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thefork.challenge.pokemon.presentation.R
import com.thefork.challenge.pokemon.presentation.theme.PokemonTheme

@Composable
fun PokemonErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(modifier = modifier) {
        PokemonContentHeader(speciesColor = Color.Red) {
            Image(
                painter = painterResource(R.drawable.pokemon_logo_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }
        ErrorContentBody(stringResource(R.string.loading_error_message)) { onRetry() }
    }
}

@Composable
private fun ErrorContentBody(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors()
                    .copy(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
            ) {
                Text(stringResource(R.string.retry_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonErrorContentPreview() {
    PokemonTheme {
        PokemonErrorContent() {}
    }
}