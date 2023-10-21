package com.anafthdev.composeresearch.research.navigate_with_result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import timber.log.Timber

@Composable
fun Screen2(
    viewModel: Screen2ViewModel,
    navController: NavController
) {

    val previousViewModel = hiltViewModel<Screen1ViewModel>(navController.previousBackStackEntry!!)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {
                Timber.i("bek prev: ${navController.previousBackStackEntry?.savedStateHandle?.get<String>("text")}")

                previousViewModel.savedStateHandle
                    ?.set("text", "Ju Jingyi <3")

                Timber.i("bek next: ${navController.previousBackStackEntry?.savedStateHandle?.get<String>("text")}")

                navController.navigateUp()
            }
        ) {
            Text("Back with \"Ju Jingyi <3\"")
        }
    }

}
