package com.anafthdev.composeresearch.research.modifier.size

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true, device = "spec:width=392.7dp,height=850.9dp,dpi=440")
@Composable
fun AspectRatioEx() {

    val density = LocalDensity.current

    var size by remember { mutableStateOf("") }

    val boxWidth = 120.dp
    val aspectRatioModifier = Modifier
        .aspectRatio(
            ratio = 2f/1f,
            matchHeightConstraintsFirst = false
        )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(boxWidth)
                .height(40.dp)
                .then(aspectRatioModifier)
                .background(Color.Blue)
                .onGloballyPositioned {
                    size = with(density) { "${it.size.width.toDp()}x${it.size.height.toDp()}" }
                }
        ) {
            Text(size)
        }
    }
}
