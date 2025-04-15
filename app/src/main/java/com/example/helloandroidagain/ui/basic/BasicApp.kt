package com.example.helloandroidagain.ui.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.helloandroidagain.ui.basic.screen.ConstraintScreen
import com.example.helloandroidagain.ui.basic.screen.GreetingsScreen
import com.example.helloandroidagain.ui.basic.screen.OnboardingScreen

@Composable
fun BasicApp(currentScreen: Screen, modifier: Modifier = Modifier) {
    when (currentScreen) {
        Screen.ONBOARDING -> OnboardingScreen(modifier = modifier)

        Screen.LIST -> {
            val basicViewModel: BasicViewModel = viewModel()
            val greetingUiState by basicViewModel.greetingScreenState.collectAsState()
            GreetingsScreen(uiState = greetingUiState, modifier = modifier)
        }

        Screen.CONSTRAINT -> ConstraintScreen(modifier = modifier)
    }
}
