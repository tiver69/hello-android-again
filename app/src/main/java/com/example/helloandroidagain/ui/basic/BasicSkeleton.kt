package com.example.helloandroidagain.ui.basic

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    ONBOARDING("Onboarding", Icons.Default.Star),
    LIST("List", Icons.AutoMirrored.Filled.List),
    CONSTRAINT("Constraint", Icons.Default.Build)
}

@Composable
fun BasicAppScaffold() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.ONBOARDING) }

    Scaffold(
        topBar = { BasicTopBar() },
        bottomBar = {
            BasicBottomBar(currentScreen) { newScreen -> currentScreen = newScreen }
        },
        floatingActionButton = { BasicFloatingButton() },
        floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {}
    ) { innerPadding ->
        BasicApp(currentScreen, modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicTopBar() {
    val context: Context = LocalContext.current
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
            SingleItemDropDown("Info", Icons.Default.Settings) {
                Toast.makeText(context, "Important info!", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
fun SingleItemDropDown(description: String, icon: ImageVector, action: () -> Unit) {
    Box {
        var dropDownExpanded by remember { mutableStateOf(false) }
        IconButton(onClick = { dropDownExpanded = true }) {
            Icon(
                imageVector = icon,
                contentDescription = description
            )
        }
        DropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = { dropDownExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(description) },
                onClick = {
                    dropDownExpanded = false
                    action()
                }
            )
        }
    }
}

@Composable
private fun BasicFloatingButton() {
    FloatingActionButton(onClick = {}) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "floating action"
        )
    }
}

@Composable
private fun BasicBottomBar(currentScreen: Screen, navigate: (Screen) -> Unit) {
    NavigationBar {
        Screen.entries.forEach { screen ->
            NavigationBarItem(
                selected = screen == currentScreen,
                label = { Text(screen.label) },
                onClick = { navigate(screen) },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.label
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicAppScaffoldPreview() {
    HelloAndroidAgainTheme {
        BasicAppScaffold()
    }
}
