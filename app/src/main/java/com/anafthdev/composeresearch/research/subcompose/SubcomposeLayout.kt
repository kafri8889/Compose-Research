package com.anafthdev.composeresearch.research.subcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun SubcomposeLayoutEx1() {
    val subcomposeContent: @Composable () -> Unit = {
        TwoBox()
    }

    SubcomposeLayout { constraints ->

        val mainPlaceables = subcompose("main", subcomposeContent)
            // Measure content dengan default Constraint (min: 0, max: Constraints.Infinity)
            .map { it.measure(Constraints()) }

        // Cari panjang maksimum dari child content
        val maxWidth = mainPlaceables.maxBy { it.width }

        // Measure content lagi dengan Constraint yang "minWidth"-nya sudah di ubah dengan "maxWidth"
        val modifiedPlaceables = subcompose("modified", subcomposeContent).map {
            it.measure(
                Constraints(
                    minWidth = maxWidth.width // Ubah value minWidth dengan maxWidth
                )
            )
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var height = 0

            modifiedPlaceables.forEach { placeable ->
                // Tempatkan content secara relative (RTL atau LTR)
                placeable.placeRelative(0, height)
                height += placeable.height
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TwoBox() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.Blue)
    ) {
        Text(
            text = "Text box biru",
            color = Color.White,
            modifier = Modifier
                .padding(8.dp)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.Red)
    ) {
        Text(
            text = "Text box merah ------",
            color = Color.White,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
