package com.example.helloandroidagain.ui.basic.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.helloandroidagain.R
import com.example.helloandroidagain.ui.theme.HelloAndroidAgainTheme

@Composable
fun ConstraintScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize(),
        constraintSet = constraints()
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_camera),
            contentDescription = "Test Description",
            modifier = Modifier
                .layoutId(MAIN_IMAGE)
                .background(Color.Gray)
        )

        OutlinedTextField(
            value = "Name",
            onValueChange = {},
            modifier = Modifier.layoutId(INPUT_NAME)
        )

        Row(
            modifier = Modifier
                .layoutId(INPUT_ROW)
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = "Count", onValueChange = {}, modifier = Modifier.weight(1f))
            OutlinedTextField(value = "Date", onValueChange = {}, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .layoutId(BUTTON_ROW)
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {}, modifier = Modifier.weight(1f)) {
                Text("Do")
            }
            Button(onClick = {}, modifier = Modifier.weight(1f)) {
                Text("Do Again")
            }
        }
    }
}

private const val MAIN_IMAGE = "mainImage"
private const val INPUT_NAME = "inputName"
private const val INPUT_ROW = "inputRow"
private const val BUTTON_ROW = "buttonRow"

private fun constraints() = ConstraintSet {
    val topGuideline = createGuidelineFromTop(0.08f)
    val bottomGuideline = createGuidelineFromBottom(0.16f)
    val mainImage = createRefFor(MAIN_IMAGE)
    val inputName = createRefFor(INPUT_NAME)
    val inputRow = createRefFor(INPUT_ROW)
    val buttonRow = createRefFor(BUTTON_ROW)
    val chain = createVerticalChain(
        mainImage,
        inputName,
        inputRow,
        buttonRow,
        chainStyle = ChainStyle.SpreadInside
    )

    constrain(chain) {
        top.linkTo(topGuideline)
        bottom.linkTo(bottomGuideline)
    }
    constrain(mainImage) {
        top.linkTo(topGuideline)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(inputName.top)
        width = Dimension.percent(0.9f)
        height = Dimension.ratio("4:3")
    }
    constrain(inputName) {
        top.linkTo(mainImage.bottom, margin = 16.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(inputRow.top)
        width = Dimension.percent(0.8f)
    }
    constrain(inputRow) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(inputName.bottom)
        bottom.linkTo(buttonRow.top)
    }
    constrain(buttonRow) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(inputRow.bottom)
        bottom.linkTo(bottomGuideline)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConstraintScreenPreview() {
    HelloAndroidAgainTheme {
        ConstraintScreen()
    }
}
