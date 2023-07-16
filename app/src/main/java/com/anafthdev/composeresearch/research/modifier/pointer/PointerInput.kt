package com.anafthdev.composeresearch.research.modifier.pointer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.anafthdev.composeresearch.extension.toIntOffset

//@Preview(showSystemUi = true)
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

@Preview(showSystemUi = true)
@Composable
fun PointerInputEx2() {

    var pan by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableFloatStateOf(1f) }
    var centroid by remember { mutableStateOf(Offset.Zero) }
    var rotation by remember { mutableFloatStateOf(0f) }

    val pointerInputModifier = Modifier
        .pointerInput(Unit) {
            detectTransformGestures { mCentroid, mPan, mZoom, mRotation ->
                val mmZoom = zoom * mZoom

                pan = mPan
                zoom = mmZoom.coerceIn(0.1f..5f)
                centroid = mCentroid
                rotation += mRotation
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Pan: $pan")
            Text(text = "Zoom: ${zoom}x")
            Text(text = "Centroid: $centroid")
            Text(text = "Rotation: $rotation")
        }

        Box(
            modifier = Modifier
                .size(150.dp)
                .scale(zoom)
                .rotate(rotation)
                .background(Color.Green)
                .align(Alignment.Center)
                .then(pointerInputModifier)
        ) {
            Box(
                modifier = Modifier
                    .offset { centroid.toIntOffset() }
                    .clip(CircleShape)
                    .size(24.dp)
                    .background(Color.Red)
                    .zIndex(1f)
            )
        }
    }
}
