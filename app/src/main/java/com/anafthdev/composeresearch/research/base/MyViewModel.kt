package com.anafthdev.composeresearch.research.base

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<MyState>(
    savedStateHandle = savedStateHandle,
    defaultState = MyState()
) {

    /**
     * Funtion yang digunakan untuk memperbarui teks dari text field ke [savedStateHandle]
     */
    fun updateText(newText: String) {
        updateState {
            copy(
                text = newText
            )
        }
    }

}