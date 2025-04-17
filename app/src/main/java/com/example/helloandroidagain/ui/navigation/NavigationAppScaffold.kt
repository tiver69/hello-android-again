package com.example.helloandroidagain.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.helloandroidagain.ui.navigation.component.CustomBottomBar
import com.example.helloandroidagain.ui.navigation.component.CustomNavigationBarItem
import com.example.helloandroidagain.ui.navigation.helper.navigateSingleTopTo
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun NavigationAppScaffold() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentScreen =
        navigationScreens.find { it.route == currentBackStack?.destination?.route } ?: Roll

    Scaffold(
        bottomBar = {
            if (currentScreen.isTop) {
                CustomBottomBar {
                    CustomNavigationBarItem(
                        Roll,
                        currentScreen == Roll
                    ) { navController.navigateSingleTopTo(Roll.route) }
                    CustomNavigationBarItem(
                        Ribbon,
                        currentScreen == Ribbon
                    ) { navController.navigateSingleTopTo(Ribbon.route) }
                    CustomNavigationBarItem(
                        Net,
                        currentScreen == Net
                    ) { navController.navigateSingleTopTo(Net.route) }
                }
            }
        }
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:parent=pixel_3a,navigation=buttons"
)
@Composable
fun NavigationAppScaffoldPreview() {
    HelloAndroidAgainTheme {
        NavigationAppScaffold()
    }
}
