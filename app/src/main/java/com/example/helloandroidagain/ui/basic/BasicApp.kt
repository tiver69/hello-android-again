package com.example.helloandroidagain.ui.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.helloandroidagain.ui.basic.screen.GreetingsScreen
import com.example.helloandroidagain.ui.basic.screen.OnboardingScreen

@Composable
fun BasicApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        val basicViewModel: BasicViewModel = viewModel()
        LaunchedEffect(Unit) {
            basicViewModel.initializeGreetings()
        }

        val greetingUiState by basicViewModel.greetingScreenUiState.collectAsState()
        GreetingsScreen(uiState = greetingUiState)
    }
}
