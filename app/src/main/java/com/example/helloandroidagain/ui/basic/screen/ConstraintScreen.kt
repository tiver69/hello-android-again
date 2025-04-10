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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.helloandroidagain.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConstraintScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val topGuideline = createGuidelineFromTop(0.18f)
        val bottomGuideline = createGuidelineFromBottom(0.2f)
        val (
            mainImage,
            inputName,
            inputRow,
            buttonRow
        ) = createRefs()

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

        Image(
            painter = painterResource(R.drawable.baseline_camera),
            contentDescription = "Test Description",
            modifier = Modifier
                .constrainAs(mainImage) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(inputName.top)
                    width = Dimension.percent(0.9f)
                    height = Dimension.ratio("4:3")
                }
                .background(Color.Gray)
        )

        OutlinedTextField(
            value = "Name",
            onValueChange = {},
            modifier = Modifier.constrainAs(inputName) {
                top.linkTo(mainImage.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(inputRow.top)
                width = Dimension.percent(0.8f)
            }
        )

        Row(
            modifier = Modifier
                .constrainAs(inputRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(inputName.bottom)
                    bottom.linkTo(buttonRow.top)
                }
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = "Count", onValueChange = {}, modifier = Modifier.weight(1f))
            OutlinedTextField(value = "Date", onValueChange = {}, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .constrainAs(buttonRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(inputRow.bottom)
                    bottom.linkTo(bottomGuideline)
                }
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
