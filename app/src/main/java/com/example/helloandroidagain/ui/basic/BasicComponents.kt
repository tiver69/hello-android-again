package com.example.helloandroidagain.ui.basic

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.helloandroidagain.R

@Composable
fun Greeting(
    name: String,
    uiState: GreetingScreenUiState,
    checkedStateModified: (Int, Boolean) -> Unit,
    otherTextModified: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            GreetingItemHeader(name, expanded) { expanded = !expanded }
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    CheckBoxText(uiState.checks[0], "First important decision") { checked ->
                        checkedStateModified(0, checked)
                        Log.i(
                            "GreetingsScreen",
                            "$name, 1: ${if (checked) "Checked" else "Unchecked"}"
                        )
                    }
                    CheckBoxText(uiState.checks[1], "Second important decision") { checked ->
                        checkedStateModified(1, checked)
                        Log.i(
                            "GreetingsScreen",
                            "$name, 2: ${if (checked) "Checked" else "Unchecked"}"
                        )
                    }
                    CheckBoxText(uiState.checks[2], "Third important decision") { checked ->
                        checkedStateModified(2, checked)
                        Log.i(
                            "GreetingsScreen",
                            "$name, 3: ${if (checked) "Checked" else "Unchecked"}"
                        )
                    }
                    OutlinedTextField(value = uiState.otherText, onValueChange = otherTextModified)
                }
            }
        }
    }
}

@Composable
fun GreetingItemHeader(name: String, isExpanded: Boolean, expandedChanged: () -> Unit) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Hello,")
            Text(
                text = "$name!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
        IconButton(onClick = expandedChanged) {
            Icon(
                imageVector = if (isExpanded) Filled.KeyboardArrowUp else Filled.ArrowDropDown,
                contentDescription = if (isExpanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(
                        R.string.show_more
                    )
                }
            )
        }
    }
}

@Composable
fun CheckBoxText(checked: Boolean, checkText: String, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(checkText)
    }
}
