package com.anafthdev.composeresearch.research.navigate_with_result

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import timber.log.Timber

@Composable
fun MainNavigateWithResult() {

    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = "screen1?text={text}"
    ) {
        composable(
            route = "screen1?text={text}",
            arguments = listOf(
                navArgument("text") {

                }
            )
        ) { backEntry ->
            Timber.i("bek entri: ${backEntry.savedStateHandle.get<String>("text")}")
            val vm = hiltViewModel<Screen1ViewModel>(backEntry)

            Screen1(vm, controller)
        }

        composable("screen2") { backEntry ->
            val vm = hiltViewModel<Screen2ViewModel>(backEntry)

            Screen2(vm, controller)
        }
    }
}
