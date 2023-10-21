package com.anafthdev.composeresearch.research.common

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anafthdev.composeresearch.uicomponent.FlipRotation
import com.anafthdev.composeresearch.uicomponent.FlippableCard
import com.anafthdev.composeresearch.uicomponent.rememberFlippableCardState
import kotlinx.coroutines.launch

@Composable
fun FlippableCardScreen() {

    val mutableFrontFlipRotation = remember {
        mutableStateListOf<FlipRotation>()
    }

    val mutableBackFlipRotation = remember {
        mutableStateListOf<FlipRotation>()
    }

    val coroutineScope = rememberCoroutineScope()
    val state = rememberFlippableCardState(
        frontFlipRotation = mutableFrontFlipRotation.toSet(),
        backFlipRotation = mutableBackFlipRotation.toSet(),
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            Text(text = "Current angle: ${state.currentAngle}")
        }

        item {
            Text(text = "Flip fraction: ${state.flipFraction}")
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Front")

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.X in mutableFrontFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableFrontFlipRotation.add(FlipRotation.X)
                                else mutableFrontFlipRotation.remove(FlipRotation.X)
                            }
                        )

                        Text(
                            text = "X",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.Y in mutableFrontFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableFrontFlipRotation.add(FlipRotation.Y)
                                else mutableFrontFlipRotation.remove(FlipRotation.Y)
                            }
                        )

                        Text(
                            text = "Y",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.Z in mutableFrontFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableFrontFlipRotation.add(FlipRotation.Z)
                                else mutableFrontFlipRotation.remove(FlipRotation.Z)
                            }
                        )

                        Text(
                            text = "Z",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Back")

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.X in mutableBackFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableBackFlipRotation.add(FlipRotation.X)
                                else mutableBackFlipRotation.remove(FlipRotation.X)
                            }
                        )

                        Text(
                            text = "X",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.Y in mutableBackFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableBackFlipRotation.add(FlipRotation.Y)
                                else mutableBackFlipRotation.remove(FlipRotation.Y)
                            }
                        )

                        Text(
                            text = "Y",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = FlipRotation.Z in mutableBackFlipRotation,
                            onCheckedChange = { checked ->
                                if (checked) mutableBackFlipRotation.add(FlipRotation.Z)
                                else mutableBackFlipRotation.remove(FlipRotation.Z)
                            }
                        )

                        Text(
                            text = "Z",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        item {
            FlippableCard(
                state = state,
                onClick = {
                    coroutineScope.launch {
                        state.flip(tween(512))
                    }
                },
                front = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
//                            .background(Color.Green)
                    ) {
                        Text("Front")
                    }
                }, back = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
//                            .background(Color.Yellow)
                    ) {
                        Text("Back")
                    }
                }
            )
        }

    }
}
