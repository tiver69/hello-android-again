package com.example.helloandroidagain.ui.basic

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun BasicAppScaffold() {
    Scaffold(
        topBar = { BasicTopBar() },
        bottomBar = { BasicBottomBar() },
        floatingActionButton = { BasicFloatingButton() },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {}
    ) { innerPadding ->
        BasicApp(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicTopBar() {
//    MediumTopAppBar(
//    LargeTopAppBar(
    TopAppBar(
//    CenterAlignedTopAppBar(
        title = { Text("Basic App") },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "top menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "top action 1"
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "top action 2"
                )
            }
        }
    )
}

@Composable
private fun BasicFloatingButton() {
    FloatingActionButton(onClick = {}) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "floating action"
            )
        }
    }
}

@Composable
private fun BasicBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            label = { Text("Onboarding") },
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "navigation 1"
                )
            }
        )
        NavigationBarItem(
            selected = false,
            label = { Text("List") },
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "navigation 2"
                )
            }
        )
        NavigationBarItem(
            selected = false,
            label = { Text("Constraint") },
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "navigation 3"
                )
            }
        )
    }
}
