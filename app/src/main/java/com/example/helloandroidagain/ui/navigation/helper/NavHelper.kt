package com.example.helloandroidagain.ui.navigation.helper

import androidx.navigation.NavHostController
import com.example.helloandroidagain.ui.navigation.Archive

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavHostController.navigateToArchive(startingDate: Long) =
    this.navigateSingleTopTo("${Archive.route}/$startingDate")
