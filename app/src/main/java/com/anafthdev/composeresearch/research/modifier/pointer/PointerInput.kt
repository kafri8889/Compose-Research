package com.anafthdev.composeresearch.research.modifier.pointer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anafthdev.composeresearch.extension.toIntOffset

@Preview(showSystemUi = true)
@Composable
fun PointerInputEx() {

    var tapOffset by remember { mutableStateOf(Offset.Zero) }

    val pointerInputModifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                tapOffset = offset
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(pointerInputModifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset { tapOffset.toIntOffset() }
                .size(72.dp)
                .background(Color.Green)
        ) {
            Text("x: ${tapOffset.x}, y: ${tapOffset.y}")
        }
    }
}
