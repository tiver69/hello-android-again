package com.example.helloandroidagain.ui.navigation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloandroidagain.ui.navigation.NavigationDestination
import com.example.helloandroidagain.ui.navigation.Roll
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        horizontalArrangement = Arrangement.Start
    ) {
        content()
    }
}

@Composable
fun CustomNavigationBarItem(
    destination: NavigationDestination,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
            .background(
                color = if (isActive) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    ) {
        val activePadding = if (isActive) 8.dp else 0.dp
        Icon(
            imageVector = destination.icon,
            contentDescription = destination.name
        )
        AnimatedVisibility(
            visible = isActive,
            enter = expandHorizontally(expandFrom = Alignment.Start),
            exit = shrinkHorizontally(shrinkTowards = Alignment.Start)
        ) {
            Text(destination.name, modifier = modifier.padding(horizontal = activePadding))
        }
    }
}

@Preview(backgroundColor = 0xFF00BCD4)
@Composable
fun CustomNavigationBarItemPreview() {
    HelloAndroidAgainTheme {
        CustomNavigationBarItem(Roll, true) {}
    }
}
