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

@Preview(showBackground = true)
@Composable
fun CheckedCheckBoxTextPreview() {
    HelloAndroidAgainTheme {
        CheckBoxText(true, "Some important choice") {}
    }
}
