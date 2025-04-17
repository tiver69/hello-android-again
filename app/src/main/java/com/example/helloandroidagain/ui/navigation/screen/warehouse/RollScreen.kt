package com.example.helloandroidagain.ui.navigation.screen.warehouse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.helloandroidagain.ui.navigation.Archive
import com.example.helloandroidagain.ui.navigation.Roll

@Composable
fun RollScreen(
    onClickGoToArchive: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
            .semantics { contentDescription = "${Roll.name} Screen" },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Roll")
        Button(
            modifier =
            Modifier.clearAndSetSemantics {
                contentDescription = "Go to ${Archive.route}"
            },
            onClick = { onClickGoToArchive() }
        ) {
            Text("Go To Archive")
        }
    }
}
