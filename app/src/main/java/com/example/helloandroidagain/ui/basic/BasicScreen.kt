package com.example.helloandroidagain.ui.basic

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun GreetingsScreen() {
    val basicViewModel: BasicViewModel = viewModel()

    basicViewModel.initializeGreetings()
    val greets by basicViewModel.greetingCheckedStates.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(greets.keys.toList()) { greetName ->
                Greeting(
                    name = greetName,
                    checkedState = greets[greetName]!!,
                    checkedStateModified = { position, checked ->
                        basicViewModel.updateCheckState(greetName, position, checked)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// ---------- PREVIEWS ----------
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    HelloAndroidAgainTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    HelloAndroidAgainTheme {
        GreetingsScreen()
    }
}
