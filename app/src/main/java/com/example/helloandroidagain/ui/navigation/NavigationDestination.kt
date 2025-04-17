package com.example.helloandroidagain.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

val navigationScreens = listOf(Roll, Ribbon, Net, Archive)

interface NavigationDestination {
    val icon: ImageVector
    val name: String
    val route: String
    val isTop: Boolean
}

object Roll : NavigationDestination {
    override val icon: ImageVector = Icons.Default.Info
    override val name: String = "Roll"
    override val route: String = "roll"
    override val isTop: Boolean = true
}

object Ribbon : NavigationDestination {
    override val icon: ImageVector = Icons.Default.Menu
    override val name: String = "Ribbon"
    override val route: String = "ribbon"
    override val isTop: Boolean = true
}

object Net : NavigationDestination {
    override val icon: ImageVector = Icons.Default.Close
    override val name: String = "Net"
    override val route: String = "net"
    override val isTop: Boolean = true
}

object Archive : NavigationDestination {
    override val icon: ImageVector = Icons.Default.Close
    override val name: String = "Archive"
    override val route: String = "archive"
    override val isTop: Boolean = false

    const val startingDateArg = "startingDate"
    val routeWithArgs = "$route/{$startingDateArg}"
    val arguments = listOf(
        navArgument(startingDateArg) { type = NavType.LongType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "hello_android_again://$route/{$startingDateArg}" }
    )
}
