package com.example.helloandroidagain.ui.basic.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloandroidagain.ui.basic.BasicViewModel.GreetingScreenUiState
import com.example.helloandroidagain.ui.basic.component.GreetingItem
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun GreetingsScreen(
    modifier: Modifier = Modifier,
    uiState: GreetingScreenUiState
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 4.dp)
        ) {
            items(uiState.greetings) { greetingItem ->
                GreetingItem(
                    uiState = greetingItem
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    val uiState = GreetingScreenUiState(
        greetings = List(100) { i ->
            GreetingScreenUiState.GreetingItemUiState(
                name = "Preview${i + 1}",
                checks = listOf(false, false, false),
                updateCheckState = { _, _, _ -> },
                updateOtherText = { _, _ -> }
            )
        }
    )
    HelloAndroidAgainTheme {
        GreetingsScreen(uiState = uiState)
    }
}
