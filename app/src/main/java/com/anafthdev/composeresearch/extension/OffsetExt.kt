package com.anafthdev.composeresearch.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

fun Offset.toIntOffset(): IntOffset = IntOffset(x.toInt(), y.toInt())
