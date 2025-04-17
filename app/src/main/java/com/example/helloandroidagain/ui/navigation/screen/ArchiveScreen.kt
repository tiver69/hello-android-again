package com.example.helloandroidagain.ui.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.helloandroidagain.ui.navigation.Archive
import java.time.LocalDate

@Composable
fun ArchiveScreen(
    startingDate: Long? = LocalDate.now().toEpochDay(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
            .semantics { contentDescription = "${Archive.name} Screen" },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        startingDate?.let {
            Text(
                "Archive starting from ${LocalDate.ofEpochDay(startingDate)}"
            )
        }
    }
}
