package com.example.helloandroidagain.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.helloandroidagain.ui.navigation.helper.navigateToArchive
import com.example.helloandroidagain.ui.navigation.screen.ArchiveScreen
import com.example.helloandroidagain.ui.navigation.screen.warehouse.NetScreen
import com.example.helloandroidagain.ui.navigation.screen.warehouse.RibbonScreen
import com.example.helloandroidagain.ui.navigation.screen.warehouse.RollScreen
import java.time.LocalDate

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Roll.route,
        modifier = modifier
    ) {
        composable(route = Roll.route) {
            RollScreen(
                onClickGoToArchive = {
                    val yesterday = LocalDate.now().minusDays(1).toEpochDay() // 20194
                    navController.navigateToArchive(yesterday)
                }
            )
        }
        composable(route = Ribbon.route) {
            RibbonScreen()
        }
        composable(route = Net.route) {
            NetScreen()
        }
        composable(
            route = Archive.routeWithArgs,
            arguments = Archive.arguments,
            deepLinks = Archive.deepLinks
        ) { navBackStackEntry ->
            val startingDate = navBackStackEntry.arguments?.getLong(Archive.startingDateArg)
            ArchiveScreen(startingDate = startingDate)
        }
    }
}
