package com.thefork.challenge.pokemon.presentation.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle

@Composable
fun BackgroundAwareText(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        color = if (backgroundColor.luminance() > 0.2) Color.Black else Color.White,
        style = style,
        modifier = modifier
    )
}