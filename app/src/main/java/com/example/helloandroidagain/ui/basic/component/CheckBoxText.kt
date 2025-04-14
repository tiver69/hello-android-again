package com.example.helloandroidagain.ui.basic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun CheckBoxText(
    modifier: Modifier = Modifier,
    state: CheckBoxTextState,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!state.checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = state.checked,
            onCheckedChange = onCheckedChange
        )
        Text(state.checkText)
    }
}

data class CheckBoxTextState(
    val checked: Boolean,
    val checkText: String
)

@Preview(showBackground = true)
@Composable
fun CheckedCheckBoxTextPreview() {
    HelloAndroidAgainTheme {
        CheckBoxText(
            state = CheckBoxTextState(true, "Some important choice")
        )
    }
}
